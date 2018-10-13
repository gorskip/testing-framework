package pl.pg.mocked;

import pl.pg.client.rest.Response;
import pl.pg.config.mock.Mock;
import pl.pg.config.mock.RestMock;
import pl.pg.config.rest.Expected;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.pg.verify.VerifyIf;

public class MockResponseTest {

    static Mock mock;
    RestMock restMock;

    @BeforeClass
    public static void setup() {
        mock = new Mock("mock/rest.mock.json");
    }

    @Test
    public void Should_Have_Expected_Status_And_Contains_Expected_Body() {
        restMock = mock.restMock("equal_status_contains_body");
        Response response = restMock.getResponse();
        Expected expected = restMock.getExpected();

        new VerifyIf.VerifyBuilder().build().given(response)
                .has(expected)
                .status()
                .and()
                .contains(expected)
                .body();
    }

    @Test
    public void Should_Have_Expected_Status_And_Body() {
        restMock = mock.restMock("equal_status_and_body");
        Response response = restMock.getResponse();
        Expected expected = restMock.getExpected();

        new VerifyIf.VerifyBuilder().build().given(response)
                .has(expected)
                .status()
                .body();
    }

    @Test
    public void Should_Have_Expected_Status_And_Body_With_Order() {
        restMock = mock.restMock("equal_status_and_body_with_order");
        Response response = restMock.getResponse();
        Expected expected = restMock.getExpected();

        new VerifyIf.VerifyBuilder()
                .withOrderChecking()
                .build().given(response)
                .has(expected)
                .status()
                .body();
    }
}
