package pl.pg.util;

import java.io.File;

public class ResourceUtil {

    public File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}
