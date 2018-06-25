package run;

import report.Reporter;

import java.util.List;

public class TestRunnerBuilder {

    TestRunner testRunner = new TestRunner();

    public TestRunnerBuilder withTestListeners(List<Reporter> reporters) {
        testRunner.setReporters(reporters);
        return this;
    }

    public TestRunnerBuilder addReporter(Reporter reporter) {
        testRunner.addReporter(
                reporter);
        return this;
    }

    public TestRunner build() {
        return testRunner;
    }
}
