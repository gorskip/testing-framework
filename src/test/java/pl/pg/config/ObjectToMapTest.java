package pl.pg.config;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pl.pg.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class ObjectToMapTest {

    @Test
    public void test() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("object.json").getFile());
        ObjectToMap objectToMap = JsonMapper.fromJson(FileUtils.readFileToString(file, "UTF-8"), ObjectToMap.class);
        System.out.println(objectToMap);
    }
}
