package cn.jarod.bluecat.core.data.es.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextType {

    String value() default "text";

    String analyzer() default "";

    String format() default "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis";
}
