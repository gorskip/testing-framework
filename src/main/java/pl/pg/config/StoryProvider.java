package config;

import exception.CannotReadFileException;
import json.Params;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public interface StoryProvider {

    Story getStory();

    default String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, Params.ENCODING);
        } catch (IOException e) {
            throw new CannotReadFileException("Cannot read configuration " + file, e);
        }
    }
}
