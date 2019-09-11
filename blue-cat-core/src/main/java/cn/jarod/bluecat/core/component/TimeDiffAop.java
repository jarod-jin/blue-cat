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

    ThreadLocal<Long> time = new ThreadLocal<>();


    /**
     * 在方法前记录时间并根据注解，是否打印参数
     * @param joinPoint
     * @return
     */
    @Before("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public void beforeMethod(JoinPoint joinPoint){

    }


    /**
     * 方法执行前后拦截
     * @param pjp
     * @return
     */
    @Around("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        time.set(System.currentTimeMillis());
        //获取TimeDiff注解中是否需要打印参数
        try {
            Method method = getObjMethod(pjp);
            String name = StringUtils.isEmpty(method.getAnnotation(TimeDiff.class).name())?
                    method.getName() : method.getAnnotation(TimeDiff.class).name();
            log.info("{}开始执行，开始时间：{}",name, LocalDateTime.now());
            if (method.getAnnotation(TimeDiff.class).printParams()){
                Object[] args = pjp.getArgs();
                log.info("{}请求参数：{}",name, JSON.toJSON(args));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return pjp.proceed();
    }

    private Method getObjMethod(JoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String targetName = joinPoint.getTarget().getClass().getName();
        Class targetClass = Class.forName(targetName);
        MethodSignature ms= (MethodSignature)joinPoint.getSignature();
        String methodName = ms.getMethod().getName();
        Class<?>[] par=ms.getParameterTypes();
        return targetClass.getMethod(methodName,par);
    }


    /**
     * 在方法后打印总耗时
     * @param joinPoint
     */
    @After("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public void afterMethod(JoinPoint joinPoint){
        try {
            Method method = getObjMethod(joinPoint);
            String name = method.getAnnotation(TimeDiff.class).name();
            log.info("{}执行结束，结束时间：{}，总耗时：{}ms",name, LocalDateTime.now(), System.currentTimeMillis()-time.get());
        }  catch (Exception e) {
            log.error("打印总耗时出现异常：{}",e.getMessage());
        }
    }




}
