package report;

import config.TestCase;
import json.JsonMapper;

import java.util.List;

public interface Reporter {

    void onTestSuccess(TestCase testCase);
    void onTestFailure(TestCase testCase, String failureMessage);
    void onTestSkip(TestCase testCase);
    void onTestsFinish();

    default String testResultsToJson(List<TestResult> testResults) {
        return JsonMapper.toJson(testResults);
    }
}
