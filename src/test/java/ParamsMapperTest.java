import config.TestCaseProvider;
import config.TestSuite;
import json.JsonMapper;
import config.ParamsMapper;
import org.junit.Test;

public class ParamsMapperTest extends AbstractTest {


    @Test
    public void Should_FillTestSuiteConfigWithParamsValues() {
        TestCaseProvider provider = getProvider("provider.test.json");

        TestSuite testSuite = new ParamsMapper().map(provider.getTestSuite());

        provider = getProvider("expected.parametrized.json");

        TestSuite expectedSuite = provider.getTestSuite();

        assert JsonMapper.toJson(testSuite).equals(JsonMapper.toJson(expectedSuite));


    }
    @Test
    public void Should_FillTestSuiteConfigWithParamsValues2() {

        TestCaseProvider provider = getProvider("raw.test.suite.json");
        TestSuite testSuite = new ParamsMapper().map(provider.getTestSuite());

        provider = getProvider("parametrized.test.suite.json");
        TestSuite expectedSuite = provider.getTestSuite();

        assert JsonMapper.toJson(testSuite).equals(JsonMapper.toJson(expectedSuite));
    }
}
