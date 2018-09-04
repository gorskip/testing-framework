package pl.pg.pg.mocked;

import pl.pg.config.Story;
import pl.pg.config.mock.Mock;
import pl.pg.config.mock.MockResponse;
import pl.pg.config.mock.RestMock;
import pl.pg.config.rest.Expected;
import pl.pg.exception.AssertionException;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.verify.VerifyIf;

public class ListResponseParsingTest extends AbstractTest {

    private static Story story;

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
