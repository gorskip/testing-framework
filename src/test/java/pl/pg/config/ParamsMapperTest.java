package pl.pg.config;

import config.TestSuiteProvider;
import config.TestSuite;
import json.JsonMapper;
import config.ParamsMapper;
import org.junit.Test;
import pl.pg.AbstractTest;

public class ParamsMapperTest extends AbstractTest {


    @Test
    public void Should_FillTestSuiteConfigWithParamsValues() {
        TestSuiteProvider provider = getProvider("provider.test.json");

        TestSuite testSuite = new ParamsMapper().map(provider.getTestSuite());

        provider = getProvider("expected.parametrized.json");

        TestSuite expectedSuite = provider.getTestSuite();

        assert JsonMapper.toJson(testSuite).equals(JsonMapper.toJson(expectedSuite));


    }
    @Test
    public void Should_FillTestSuiteConfigWithParamsValues2() {

        TestSuiteProvider provider = getProvider("raw.test.suite.json");
        TestSuite testSuite = new ParamsMapper().map(provider.getTestSuite());

        provider = getProvider("parametrized.test.suite.json");
        TestSuite expectedSuite = provider.getTestSuite();

        assert JsonMapper.toJson(testSuite).equals(JsonMapper.toJson(expectedSuite));
    }
}
