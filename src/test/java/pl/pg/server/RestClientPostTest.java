package pl.pg.server;

import client.RestClient;
import client.RestClientBuilder;
import client.mapper.RestResponse;
import config.ParamsMapper;
import config.ResourceConfigProvider;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Rest;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.AbstractTest;

public class RestClientPostTest extends AbstractTest {

    private static TestSuite testSuite;

    @BeforeClass
    public static void setup() {
        TestSuite rawTestSuite = new ResourceConfigProvider("rest.post.json").getTestSuite();
        testSuite = new ParamsMapper().map(rawTestSuite);
    }

    @Test
    public void Should_Create_New_Object() {
        TestCase testCase = testSuite.getTests().get(0);

        RestClient restClient = new RestClientBuilder().build();

        Rest rest = testCase.getRest();
        Expected expected = rest.getExpected();

        RestResponse response = restClient.execute(rest.getRequest());

        verifyIf.given(response)
                .contains(expected).body();
    }
}
