package pl.pg.annotation;

import pl.pg.mapper.DefaultMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MappedBy {

    Class mapper() default DefaultMapper.class;

}
