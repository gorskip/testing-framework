package pl.pg.report;

import pl.pg.config.TestCase;

public interface TestListener {

    void onTestSuccess(TestCase testCase);
    void onTestFailure(TestCase testCase, String failureMessage);
    void onTestSkipped(TestCase testCase);
    void onTestsFinish();

}
