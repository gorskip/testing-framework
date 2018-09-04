package config;

import exception.StoryFileNotFoundException;
import json.JsonMapper;

import java.io.File;

public class FileProvider implements StoryProvider {

    private final String filePath;

    public FileProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Story getStory() {
        File file = new File(filePath);
        if(!file.exists()) {
            throw new StoryFileNotFoundException(file.getAbsolutePath());
        }
        return JsonMapper.fromJson(getFileContent(file), Story.class);
    }
}
