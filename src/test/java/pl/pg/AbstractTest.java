package pl.pg;

import verify.VerifyIf;
import config.TestSuiteProvider;
import config.TestSuite;
import exception.CannotReadFileException;
import json.JsonMapper;
import json.Params;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class AbstractTest {

    protected VerifyIf verifyIf = new VerifyIf();

    protected TestSuiteProvider getProvider(String fileName) {
        return  new TestSuiteProvider() {

            public TestSuite getTestSuite() {
                return get(fileName);
            }
        };
    }

    protected TestSuite get(String fileName) {
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
