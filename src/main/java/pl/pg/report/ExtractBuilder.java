package pl.pg.report;

import pl.pg.config.TestCase;
import pl.pg.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ExtractBuilder implements TestListener {

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
    public void onTestSkipped(TestCase testCase) {
        TestResult testResult = new TestResult();
        testResult.setStatus(Status.SKIPPED);
        testResult.setTestCase(testCase);
        testResults.add(testResult);
    }

    @Override
    public void onTestsFinish() {
        FileUtil.saveAsJson("test-pl.pg.report.pl.pg.json", testResults);
    }
}
