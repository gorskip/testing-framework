package verify;

import client.mapper.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import config.rest.Expected;
import exception.AssertionException;
import json.JsonMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import verify.diff.Diff;
import verify.diff.DiffSummarize;
import verify.diff.DiffSummarizeBuilder;
import verify.diff.MoveDiff;

import java.util.ArrayList;
import java.util.List;

public class VerifyIf {

    private Response response;
    private Expected expected;
    private AssertType assertType;
    private Object expectedBody;
    private Object responseBody;
    private boolean verifyOrder = false;
    private DiffSummarizeBuilder diffSummarizeBuilder;

    private void setExpectedBody(Expected expected) {
        if(expected != null && this.expectedBody == null) {
            this.expectedBody = expected.getBody();
        }
    }

    private void setResponsedBody(Response response) {
        if(response != null && this.responseBody == null) {
            this.responseBody = response.getBody();
        }
    }

    public VerifyIf and() {
        return this;
    }

    public VerifyIf and(Expected expected){
        setExpected(expected);
        return this;
    }

    public VerifyIf given(Response response) {
        this.response = response;
        setResponsedBody(response);
        return this;
    }

    public VerifyIf has(Expected expected) {
        setExpected(expected);
        assertType = AssertType.EQUAL;
        return this;
    }

    public VerifyIf contains(Expected expected) {
        assertType = AssertType.CONTAIN;
        setExpected(expected);
        return this;
    }

    public VerifyIf body() {
        DiffSummarize diffSummarize = getDiffSummarize(JsonMapper.getDiff(expectedBody, responseBody));
        verifyDiff(diffSummarize, "Body: ");
        return this;
    }

    public VerifyIf headers() {
//        TODO: implement
        throw new NotImplementedException();
    }

    public VerifyIf status() {
        if(expected.getStatus() == response.getStatus()) {
            return this;
        }
        throw new AssertionError("Incorrect status code: Expected["
             + expected.getStatus()
                + "] Response ["
                + response.getStatus()
                + "]");
    }

    private VerifyIf verifyDiff(DiffSummarize diff, String failMessage) {
        switch(assertType) {
            case EQUAL: verifyEquals(diff, failMessage.concat("Response and Expected are not equal"));
            break;
            case CONTAIN: verifyContain(diff,failMessage.concat("Response does not contains Expected"));
        }

        return this;
    }

    private void verifyEquals(DiffSummarize diff, String message) {
        if(areEqual(diff)) {
            return;
        }
        throwAssertion(diff, message);
    }

    private boolean areEqual(DiffSummarize diff) {
        if(verifyOrder) {
            return !diff.hasDiffs();
        }
        return hasOnlyMoveDiffs(diff);
    }

    private boolean hasOnlyMoveDiffs(DiffSummarize diff) {
        return diff.getDiffs().size() == diff.getMoved().size();
    }

    private void verifyContain(DiffSummarize diff, String message) {
        if(areEqual(diff)) {
            return;
        }
        if(!diff.hasRemoved() && !diff.hasChanged()){
            return;
        }

        diff.setAdded(new ArrayList<>());
        throwAssertion(diff, message);
    }

    private void throwAssertion(DiffSummarize diff, String message) {
        throw new AssertionException(message+":\n"
                .concat(pretty(diff)));
    }

    private void setExpected(Expected expected) {
        this.expected = expected;
        setExpectedBody(expected);
    }

    private String pretty(DiffSummarize diff) {
        return JsonMapper.toJson(diff);
    }

    public DiffSummarize getDiffSummarize(JsonNode patchNode) {
        List diffs = JsonMapper.fromJson(patchNode.toString(), new TypeReference<List<Diff>>() {});
        List moveDiffs = JsonMapper.fromJson(patchNode.toString(), new TypeReference<List<MoveDiff>>(){});
        return new DiffSummarizeBuilder(diffs)
                .withAdded()
                .withRemoved()
                .withMoved(moveDiffs)
                .withChanged(JsonMapper.toJson(expected.getBody()))
                .build();
    }

    private void enableOrderChecking() {
        this.verifyOrder = true;
    }

    public static class VerifyBuilder {

        private VerifyIf verifyIf = new VerifyIf();

        public VerifyIf build() {
            return verifyIf;
        }

        public VerifyBuilder withOrderChecking() {
            verifyIf.enableOrderChecking();
            return this;
        }
    }


}
