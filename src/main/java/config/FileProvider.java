package config;

import json.JsonMapper;

import java.io.File;

public class FileProvider implements TestSuiteProvider {

    private final String filePath;

    public FileProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public TestSuite getTestSuite() {
         return JsonMapper.fromJson(getFileContent(new File(filePath)), TestSuite.class);
    }
}
