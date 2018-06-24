import client.RestClient;
import client.RestClientBuilder;
import client.mapper.RestResponse;
import config.ResourceConfigProvider;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Rest;
import json.ParamsMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class RestClientPostTest extends AbstractTest {

    private static TestSuite testSuite;

    @BeforeClass
    public static void setup() {
        TestSuite rawTestSuite = new ResourceConfigProvider("rest.post.json").getTestSuite();
        testSuite = new ParamsMapper().map(rawTestSuite);
    }


    @Test
    public void Should_Create_New_Object() throws IOException {
        TestCase testCase = testSuite.getFirstTest();

        RestClient restClient = new RestClientBuilder().build();

        Rest rest = testCase.getRest();
        Expected expected = rest.getExpected();

        RestResponse response = restClient.execute(rest.getRequest());

        //mock contains expected object
//        assertContains(expected, mock);

        // PROBABLY EQUALS,
//        TODO: check if mock is object or array

        verifyIf.given(response)
                .contains(expected).body();


    }


}
