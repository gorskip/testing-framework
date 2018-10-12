package pl.pg.util;

import pl.pg.exception.CannotCreateInstanceOfClass;

public class InstanceUtil {

    public static Object createInstanceOf(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CannotCreateInstanceOfClass(clazz.getName(), e);
        }
    }
}
