package pl.pg.util;

import pl.pg.exception.CannotWriteFileException;
import pl.pg.json.JsonMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static final String ENCODING = "UTF-8";

    public static void saveAsJson(String filePath, Object object) {
        try {
            FileUtils.write(new File(filePath), JsonMapper.toJson(object), ENCODING);
        } catch (IOException e) {
            throw new CannotWriteFileException(filePath, e);
        }
    }

    public static File getResource(String fileName) {
        return new ResourceUtil().getFile(fileName);
    }

}
