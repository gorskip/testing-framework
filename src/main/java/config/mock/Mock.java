package config.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import exception.CannotReadFileException;
import json.JsonMapper;
import json.Params;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Mock {

    private final String sourceName;

    public Mock(String sourceName) {
        this.sourceName = sourceName;
    }

    public RestMock restMock(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(sourceName).getFile());
        return JsonMapper.fromJson(getFileContent(file), new TypeReference<List<RestMock>>() {})
                .stream()
                .filter(mock -> name.equals(mock.getName()))
                .collect(Collectors.toList()).get(0);
    }

    private String getFileContent(File file) {
        try {
            return FileUtils.readFileToString(file, Params.ENCODING);
        } catch (IOException e) {
            throw new CannotReadFileException("Cannot read configuration " + file, e);
        }
    }
}
