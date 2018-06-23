import client.RestClient;
import client.RestClientBuilder;
import client.mapper.Response;
import config.ResourceConfigProvider;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Rest;
import json.JsonMapper;
import json.ParamsMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.skyah.comparator.JSONCompare;

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

        Response response = restClient.execute(rest.getRequest());

        //response contains expected object
//        assertContains(expected, response);

        // PROBABLY EQUALS,
//        TODO: check if response is object or array

        verifyIf.given(response)
                .contains(expected).body();


    }

    private void assertContains(Expected expected, Response response) {
        String expectedObject = JsonMapper.toJson(expected.getBody());
        String responseObject = JsonMapper.toJson(response.getBody());
        JSONCompare.assertEquals(expectedObject, responseObject);
    }

}
