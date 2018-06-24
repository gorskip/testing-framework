package run;

import config.ResourceConfigProvider;
import config.TestSuite;
import config.ParamsMapper;

public class Main {

    public static void main(String[] args) {

        TestSuite rawTestSuite = new ResourceConfigProvider("test.runner.json").getTestSuite();
        TestSuite testSuite = new ParamsMapper().map(rawTestSuite);

        new TestRunner()
                .run(testSuite);

    }
}