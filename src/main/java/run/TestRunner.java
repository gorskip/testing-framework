package run;

import client.RestClient;
import client.RestClientBuilder;
import client.mapper.Response;
import config.TestCase;
import config.TestSuite;
import config.rest.Expected;
import config.rest.Request;
import config.rest.Rest;
import report.Reporter;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private final RestClient restClient;

    private List<Reporter> reporters = new ArrayList<>();

    public TestRunner() {
        this.restClient = new RestClientBuilder().build();
    }

    public void setReporters(List<Reporter> reporters) {
        this.reporters = reporters;
    }

    public TestRunner run(TestSuite testSuite) {
        System.out.println("Running Test Suite: " + testSuite.getName());
        runTests(testSuite.getTests());
        return this;
    }

    public void runTests(List<TestCase> tests) {
        tests.forEach(test -> run(test));
    }

    private void run(TestCase test) {
        System.out.println("Test Case: " + test.getName());
        try {
            runRest(test.getRest());
            runOnTestSuccessListeners(test);
        }catch(Exception | Error e) {
            runOnTestFailireListeners(test, e.getMessage());
        }
    }

    private void runRest(Rest rest) {
        Request request = rest.getRequest();
        Expected expected = rest.getExpected();

        Response response = restClient.execute(request);
        new TestVerificator(expected).verify(response);

    }

    private void runOnTestSuccessListeners(TestCase test) {
        reporters.forEach(reporter ->
                reporter.onTestSuccess(test)
        );
    }

    private void runOnTestFailireListeners(TestCase test, String failureMessage) {
        reporters.forEach(reporter ->
                reporter.onTestFailure(test, failureMessage)
        );
    }

    public void addReporter(Reporter reporter) {
        reporters.add(reporter);
    }

    public void report() {
        reporters.forEach(reporter -> reporter.onTestsFinish());
    }
}
