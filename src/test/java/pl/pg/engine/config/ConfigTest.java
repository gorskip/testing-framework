package pl.pg.engine.config;

import org.junit.Test;
import pl.pg.AbstractTest;
import pl.pg.json.JsonMapper;

import java.io.File;

public class ConfigTest  extends AbstractTest {

    @Test
    public void ShouldParseConfig() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("z_other/config.json").getFile());
        Config config = JsonMapper.fromJson(getFileContent(file), Config.class);

        System.out.println(config);

    }
}
