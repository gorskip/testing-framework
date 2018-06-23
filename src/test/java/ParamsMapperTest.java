import config.TestCaseProvider;
import config.TestSuite;
import json.JsonMapper;
import json.ParamsMapper;
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
}
