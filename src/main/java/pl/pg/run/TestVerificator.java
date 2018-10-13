package pl.pg.run;

import pl.pg.client.rest.Response;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Type;
import pl.pg.verify.VerifyIf;

public class TestVerificator {

    private final Expected expected;
    private final Type type;

    public TestVerificator(Expected expected) {
        this.expected = expected;
        this.type = expected.getType();
    }

    public void verify(Response response) {
        if(type == null) {
            verifyIfEqualStatusAndContainsBodyAndHeaders(verifyWithoutOrder(), response);
        } else {
            if(isStatusEqual() && isBodyContain()) {
                verifyIfEqualStatusAndContainsBodyAndHeaders(response);
                return;
            }
            if(isStatusEqual() && isBodyEqual()) {
                verifyIfEqualStatusBodyAndHeaders(response);
                return;
            }
        }
    }


//    TODO: Add headers checking
    private void verifyIfEqualStatusAndContainsBodyAndHeaders(Response response) {
        if(type.isWithOrder()) {
            verifyIfEqualStatusAndContainsBodyAndHeaders(verifyWithOrder(), response);
        }else {
            verifyIfEqualStatusAndContainsBodyAndHeaders(verifyWithoutOrder(), response);
        }
    }
//    TODO: Add headers checking
    private void verifyIfEqualStatusBodyAndHeaders(Response response) {
        if(type.isWithOrder()) {
            verifyIfEqualStatusBodyAndHeaders(verifyWithOrder(), response);
        }else {
            verifyIfEqualStatusBodyAndHeaders(verifyWithoutOrder(), response);
        }
    }

    private void verifyIfEqualStatusAndContainsBodyAndHeaders(VerifyIf verifyIf, Response response) {
        verifyIf.given(response)
                .has(expected)
                .status()
                .and()
                .contains(expected)
                .body();
    }

    private void verifyIfEqualStatusBodyAndHeaders(VerifyIf verifyIf, Response response) {
        verifyIf.given(response)
                .has(expected)
                .status()
                .and(expected)
                .body();
    }

    private VerifyIf verifyWithOrder() {
        return new VerifyIf.VerifyBuilder().withOrderChecking().build();
    }

    private VerifyIf verifyWithoutOrder() {
        return new VerifyIf.VerifyBuilder().withOrderChecking().build();
    }

    private boolean isStatusEqual() {
        return "EQUAL".equals(type.getStatus());
    }

    private boolean isStatusContain() {
        return "CONTAIN".equals(type.getStatus());
    }

    private boolean isBodyEqual() {
        return "EQUAL".equals(type.getBody());
    }

    private boolean isBodyContain() {
        return "CONTAIN".equals(type.getBody());
    }
}
