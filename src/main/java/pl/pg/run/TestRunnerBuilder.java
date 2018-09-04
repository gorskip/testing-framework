package run;

import report.TestListener;

import java.util.List;

public class TestRunnerBuilder {

    TestRunner testRunner = new TestRunner();

    public TestRunnerBuilder withTestListeners(List<TestListener> testListeners) {
        testRunner.setTestListeners(testListeners);
        return this;
    }

    public TestRunnerBuilder addReporter(TestListener testListener) {
        testRunner.addReporter(
                testListener);
        return this;
    }

    public TestRunner build() {
        return testRunner;
    }
}
