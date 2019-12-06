package cn.jarod.bluecat.core.component;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @auther jarod.jin 2019/9/11
 */
@Slf4j
@Aspect
@Component
public class TimeDiffAop {

    //创建的时间变量只能被当前线程访问
    private ThreadLocal<Long> time = new ThreadLocal<>();

    //打印方法名
    private ThreadLocal<String> name = new ThreadLocal<>();

    /**
     * 在方法前记录时间并根据注解，是否打印参数
     * @return
     */
    @Before("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public void beforeMethod(){

    }


    /**
     * 方法执行前后拦截
     * @param joinPoint
     * @return
     */
    @Around("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        time.set(System.currentTimeMillis());
        try {
            Method method = getObjMethod(joinPoint);
            name.set(StringUtils.isEmpty(method.getAnnotation(TimeDiff.class).name())?
                    method.getName() : method.getAnnotation(TimeDiff.class).name());
            log.info("{}开始执行，开始时间：{}",name.get(), LocalDateTime.now());
            //获取TimeDiff注解中是否需要打印参数
            if (method.getAnnotation(TimeDiff.class).printParams()){
                Object[] args = joinPoint.getArgs();
                log.info("{}请求参数：{}",name.get(), JSON.toJSON(args));
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            log.error("打印方法开始时间时出现异常：{}", e.getMessage());
        }
        return joinPoint.proceed();
    }


    /**
     * 在方法后打印总耗时
     */
    @After("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public void afterMethod(){
        log.info("{}执行结束，结束时间：{}，总耗时：{}ms",name.get(), LocalDateTime.now(), System.currentTimeMillis()-time.get());
    }


    /**
     * 获取注解方法
     * @param joinPoint
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private Method getObjMethod(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String targetName = joinPoint.getTarget().getClass().getName();
        MethodSignature ms= (MethodSignature)joinPoint.getSignature();
        String methodName = ms.getMethod().getName();
        Class<?>[] par=ms.getParameterTypes();
        return Class.forName(targetName).getMethod(methodName,par);
    }

}
