package cn.jarod.bluecat.core.component;

import cn.jarod.bluecat.core.annotation.TimeDiff;
import cn.jarod.bluecat.core.common.Constant;
import cn.jarod.bluecat.core.utils.JsonUtil;
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
 * @author jarod.jin 2019/9/11
 */
@Slf4j
@Aspect
@Component
public class TimeDiffAop {

    /**
     * 创建的时间变量只能被当前线程访问
     */
    private ThreadLocal<Long> time = new ThreadLocal<>();

    /**
     * 打印方法名
     */
    private ThreadLocal<String> name = new ThreadLocal<>();

    /**
     * 在方法前打印开始时间
     */
    @Before("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public void beforeMethod(){
        log.info(Constant.Symbol.BRACE + " 开始执行，开始时间为："+ Constant.Symbol.BRACE,name.get(), LocalDateTime.now());
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
            //获取TimeDiff注解中是否需要打印参数
            if (method.getAnnotation(TimeDiff.class).printParams()){
                Object[] args = joinPoint.getArgs();
                log.info(Constant.Symbol.BRACE + " 的请求参数为："+ Constant.Symbol.BRACE,name.get(), JsonUtil.toJson(args));
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            log.error("TimeDiffAop执行时出现异常：" + Constant.Symbol.BRACE, e.getMessage());
        }
        return joinPoint.proceed();
    }


    /**
     * 在方法后打印总耗时
     */
    @After("@annotation(cn.jarod.bluecat.core.annotation.TimeDiff)")
    public void afterMethod(){
        log.info(Constant.Symbol.BRACE +
                " 执行结束，结束时间：" +
                Constant.Symbol.BRACE +
                "，总耗时：" +
                Constant.Symbol.BRACE +
                "ms",name.get(), LocalDateTime.now(), System.currentTimeMillis()-time.get());
        clearThreadLocal();
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

    /**
     * 删除线程变量
     */
    private void clearThreadLocal(){
        name.remove();
        time.remove();
    }

}
