package cn.jarod.bluecat.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jarod.jin 2019/9/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TimeDiff {
    /**
     * 业务名称描述
     * @return
     */
    String name() default "";

    /**
     * 是否对请求参数进行校验
     * @return
     */
    boolean printParams() default true;

}
