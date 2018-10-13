package pl.pg.run;

import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.client.rest.Response;
import pl.pg.engine.config.TestCase;
import pl.pg.engine.config.Story;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Request;
import pl.pg.engine.config.rest.Rest;
import pl.pg.report.ExtractBuilder;
import pl.pg.report.TestListener;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private final RestClient restClient;

    private List<TestListener> testListeners = new ArrayList<>();

    public TestRunner() {
        this.restClient = new RestClientBuilder().build();
        testListeners.add(new ExtractBuilder());
    }

    public void setTestListeners(List<TestListener> testListeners) {
        this.testListeners = testListeners;
    }

    public TestRunner run(Story story) {
        runTests(story.getTests());
        return this;
    }

    private void runTests(List<TestCase> tests) {
        tests.forEach(test -> run(test));
    }

    private void run(TestCase test) {
        try {
            runRest(test.getRest());
            runOnTestSuccess(test);
        }catch(Exception e) {
            runOnTestFailure(test, e.getMessage());
        }
    }

    private void runRest(Rest rest) {
        Request request = rest.getRequest();
        Expected expected = rest.getExpected();

        Response response = restClient.execute(request);
        new TestVerificator(expected).verify(response);

    }

    private void runOnTestSuccess(TestCase test) {
        testListeners.forEach(reporter ->
                reporter.onTestSuccess(test)
        );
    }

    private void runOnTestFailure(TestCase test, String failureMessage) {
        testListeners.forEach(reporter ->
                reporter.onTestFailure(test, failureMessage)
        );
    }

    public void addReporter(TestListener testListener) {
        testListeners.add(testListener);
    }

    public void report() {
        testListeners.forEach(reporter -> reporter.onTestsFinish());
    }
}
