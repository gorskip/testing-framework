import client.RestClient;
import client.RestClientBuilder;
import client.entity.Insight;
import client.mapper.RestResponse;
import config.ResourceConfigProvider;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Request;
import config.rest.Rest;
import json.JsonMapper;
import config.ParamsMapper;
import org.junit.BeforeClass;
import org.junit.Test;

public class RestClientTest extends AbstractTest {

    private static TestSuite testSuite;

    @BeforeClass
    public static void setup() {
        TestSuite rawTestSuite = new ResourceConfigProvider("restclient.json").getTestSuite();
        testSuite = new ParamsMapper().map(rawTestSuite);
    }

    @Test
    public void Should_Return_Valid_Object_By_GET_Method() {
        RestClient restClient = new RestClientBuilder().build();

        Request request = testSuite.getFirstTest().getRest().getRequest();
        System.out.println(JsonMapper.toJson(request));

        RestResponse<Insight> response = restClient.execute(request, Insight.class);

        assert 200 == response.getStatus();
        String header = response.getHeaders().getFirst("Content-Type");
        assert header.contains("application/json;charset=UTF-8");
        Insight insight = response.getBody();
//        assert 1 == insight.getId();
        assert "First stupid insight".equals(insight.getMessage());
        assert "gorskip".equals(insight.getAuthor());
    }

    @Test
    public void Should_Validate_Response_Against_Expected_Value() {
        RestClient restClient = new RestClientBuilder().build();

        TestCase test = testSuite.getFirstTest();
        Rest rest = test.getRest();
        Request request = rest.getRequest();

        RestResponse<Insight> response = restClient.execute(request, Insight.class);

        Expected expected = rest.getExpected();
        assert expected.getBodyAs(Insight.class).equals(response.getBody());

    }

    @Test
    public void Should_Validate_Response_Using_Own_Verifier() {
        RestClient restClient = new RestClientBuilder().build();

        TestCase test = testSuite.getFirstTest();
        Rest rest = test.getRest();
        Request request = rest.getRequest();

        RestResponse<Insight> response = restClient.execute(request, Insight.class);
        Expected expected = rest.getExpected();

        verifyIf.given(response)
                .has(expected).status()
                .and(expected).body()
                .and()
                .contains(expected);
    }

}
