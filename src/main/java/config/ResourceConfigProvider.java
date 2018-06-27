package config;

import json.JsonMapper;

import java.io.File;

public class ResourceConfigProvider implements TestSuiteProvider {

    private String fileName;

    public ResourceConfigProvider(String fileName) {
        this.fileName = fileName;
    }

    public ResourceConfigProvider() {}

    public TestSuite getTestSuite() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return JsonMapper.fromJson(getFileContent(file), TestSuite.class);
    }

    public File getResource(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(name).getFile());
    }
}
