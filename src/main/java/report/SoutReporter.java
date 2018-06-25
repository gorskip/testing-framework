package report;

import config.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SoutReporter implements Reporter {

    private List<TestResult> testResults = new ArrayList<>();

    @Override
    public void onTestSuccess(TestCase testCase) {
        TestResult testResult = new TestResult();
        testResult.setStatus(Status.SUCCESS);
        testResult.setTestCase(testCase);
        testResults.add(testResult);
    }

    @Override
    public void onTestFailure(TestCase testCase, String failureMessage) {
        TestResult testResult = new TestResult();
        testResult.setStatus(Status.FAILURE);
        testResult.setTestCase(testCase);
        testResult.setFailureMessage(failureMessage);
        testResults.add(testResult);
    }

    @Override
    public void onTestSkip(TestCase testCase) {
        TestResult testResult = new TestResult();
        testResult.setStatus(Status.SKIPPED);
        testResult.setTestCase(testCase);
        testResults.add(testResult);
    }

    @Override
    public void onTestsFinish() {
        System.out.println(testResultsToJson(testResults));
    }


}
