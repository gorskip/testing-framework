package pl.pg;

import verify.VerifyIf;
import config.StoryProvider;
import config.Story;
import exception.CannotReadFileException;
import json.JsonMapper;
import json.Params;
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

    private String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, Params.ENCODING);
        } catch (IOException e) {
            throw new CannotReadFileException("Cannot read configuration " + file, e);
        }
    }
}
