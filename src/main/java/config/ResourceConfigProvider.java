package config;

import exception.CannotReadFileException;
import json.JsonMapper;
import json.Params;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ResourceConfigProvider implements TestCaseProvider {

    private final String fileName;

    public ResourceConfigProvider(String fileName) {
        this.fileName = fileName;
    }

    public TestSuite getTestSuite() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return JsonMapper.fromJson(getFileContent(file), TestSuite.class);
    }

    private String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, Params.ENCODING);
        } catch (IOException e) {
            throw new CannotReadFileException("Cannot read configuration " + file, e);
        }
    }
}
