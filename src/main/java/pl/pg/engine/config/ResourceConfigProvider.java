package pl.pg.engine.config;

import pl.pg.json.JsonMapper;

import java.io.File;

public class ResourceConfigProvider implements StoryProvider {

    private String fileName;

    public ResourceConfigProvider(String fileName) {
        this.fileName = fileName;
    }

    public ResourceConfigProvider() {}

    public Story getStory() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return JsonMapper.fromJson(getFileContent(file), Story.class);
    }

    public File getResource(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(name).getFile());
    }
}
