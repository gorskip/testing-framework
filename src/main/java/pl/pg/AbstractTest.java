package pl.pg;

import pl.pg.verify.VerifyIf;
import pl.pg.engine.config.StoryProvider;
import pl.pg.engine.config.Story;
import pl.pg.exception.CannotReadFileException;
import pl.pg.json.JsonMapper;
import pl.pg.json.Params;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class AbstractTest {

    protected VerifyIf verifyIf = new VerifyIf();

    protected StoryProvider getProvider(String fileName) {
        return  new StoryProvider() {

            public Story getStory() {
                return get(fileName);
            }
        };
    }

    protected Story get(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return JsonMapper.fromJson(getFileContent(file), Story.class);
    }

    protected String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, Params.ENCODING);
        } catch (IOException e) {
            throw new CannotReadFileException("Cannot read configuration " + file, e);
        }
    }
}
