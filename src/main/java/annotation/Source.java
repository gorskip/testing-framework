package annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Source {

    String rest() default StringUtils.EMPTY;
    String db() default StringUtils.EMPTY;
    String mapper() default StringUtils.EMPTY;
}
