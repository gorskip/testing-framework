package pl.pg;

import org.apache.commons.io.FileUtils;
import pl.pg.engine.config.*;
import pl.pg.exception.CannotReadFileException;
import pl.pg.json.JsonMapper;
import pl.pg.json.Params;
import pl.pg.util.InstanceUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

//TODO: Change to Singleton
public class StoryProcessor {

    private final Map<String , Story> stories;

    public StoryProcessor() {
        this.stories = buildStories();
    }

    public Config getConfig() {
        Path resourceDirectory = Paths.get("src","test","resources");
        File file = resourceDirectory.resolve("config.json").toFile();
        return JsonMapper.fromJson(getFileContent(file), Config.class);
    }

    private String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, Params.ENCODING);
        } catch (
                IOException e) {
            throw new CannotReadFileException("Cannot read configuration " + file, e);
        }
    }

    public Map<String, Story> buildStories() {
        IParamsMapper mapper = new ParamsMapper();
        Path resourceDirectory = Paths.get("src","test","resources", "stories");
        Collection<File> storyFiles = FileUtils.listFiles(resourceDirectory.toFile(), new String[]{"json"}, true);
        return storyFiles.stream()
                .map(file -> JsonMapper.fromJson(getFileContent(file), Story.class))
                .collect(Collectors.toMap(k -> k.getName(), v -> mapper.map(v)));

    }

    public Map<String, Story> getStories() {
        return stories;
    }

    public Story getStory(String storyName) {
        return stories.get(storyName);
    }

    public Story getStory(Class<pl.pg.annotation.Story> story) {
        String storyName = ((pl.pg.annotation.Story) InstanceUtil.createInstanceOf(story)).value();
        return getStory(storyName);
    }

}
