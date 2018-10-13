package pl.pg.engine.mocked;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.engine.AbstractTest;
import pl.pg.engine.config.Story;
import pl.pg.engine.config.mock.Mock;
import pl.pg.engine.config.mock.MockResponse;
import pl.pg.engine.config.mock.RestMock;
import pl.pg.engine.config.rest.Expected;
import pl.pg.exception.AssertionException;
import pl.pg.verify.VerifyIf;

public class ListResponseParsingTest extends AbstractTest {

    private static Story story;

    static Mock mock;
    RestMock restMock;

    @BeforeClass
    public static void setup() {
        mock = new Mock("z_other/mock/rest.mock.json");
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
