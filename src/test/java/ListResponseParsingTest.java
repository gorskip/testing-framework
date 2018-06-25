import client.RestClient;
import client.RestClientBuilder;
import client.mapper.Response;
import config.ResourceConfigProvider;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Rest;
import config.ParamsMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import verify.VerifyIf;

public class ListResponseParsingTest extends  AbstractTest {

    private static TestSuite testSuite;

    @BeforeClass
    public static void setup() {
        TestSuite rawTestSuite = new ResourceConfigProvider("list.parsing.json").getTestSuite();
        testSuite = new ParamsMapper().map(rawTestSuite);
    }

    @Test
    public void Should_Verify_List_Body_Response_Without_Order() {
        TestCase testCase = testSuite.getTests().get(0);
        RestClient restClient = new RestClientBuilder().build();
        for(int i=0; i< 1000; i++) {
            Rest rest = testCase.getRest();
            Expected expected = rest.getExpected();

            Response response = restClient.execute(rest.getRequest());

            verifyIf = new VerifyIf.VerifyBuilder().build();
            verifyIf.given(response)
                    .has(expected)
                    .body();
        }
    }

    @Test
    public void Should_Verify_List_Body_Response_With_Order() {
        TestCase testCase = testSuite.getTests().get(0);
        RestClient restClient = new RestClientBuilder().build();
        for(int i=0; i< 1000; i++) {
            Rest rest = testCase.getRest();
            Expected expected = rest.getExpected();

            Response response = restClient.execute(rest.getRequest());

            verifyIf = new VerifyIf.VerifyBuilder().withOrderChecking().build();
            verifyIf.given(response)
                    .has(expected)
                    .body();
        }
    }


}
