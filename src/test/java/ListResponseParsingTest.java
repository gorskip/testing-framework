import config.TestSuite;
import config.mock.Mock;
import config.mock.MockResponse;
import config.mock.RestMock;
import config.rest.Expected;
import exception.AssertionException;
import org.junit.BeforeClass;
import org.junit.Test;
import verify.VerifyIf;

public class ListResponseParsingTest extends  AbstractTest {

    private static TestSuite testSuite;

    static Mock mock;
    RestMock restMock;

    @BeforeClass
    public static void setup() {
        mock = new Mock("mock/rest.mock.json");

    }

    @Test
    public void Should_Verify_List_Body_Response_Without_Order() {
        RestMock restMock = mock.restMock("list_response");

        MockResponse response = restMock.getResponse();
        Expected expected = restMock.getExpected();

        verifyIf = new VerifyIf.VerifyBuilder().build();
            verifyIf.given(response)
                    .contains(expected)
                    .body();
    }

    @Test(expected = AssertionException.class)
    public void Should_Verify_List_Body_Response_With_Order() {
        RestMock restMock = mock.restMock("list_response_with_order");

        MockResponse response = restMock.getResponse();
        Expected expected = restMock.getExpected();

        verifyIf = new VerifyIf.VerifyBuilder().withOrderChecking().build();
        verifyIf.given(response)
                .has(expected)
                .body();
    }


}
