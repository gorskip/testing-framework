package run;

import config.ParamsMapper;
import config.ResourceConfigProvider;
import config.TestSuite;
import report.SoutReporter;

public class Main {

    public static void main(String[] args) {

        String testSuiteConfigurationPath;
        if(args != null && args.length > 0 ) {
            testSuiteConfigurationPath = args[0];
        }else {
            testSuiteConfigurationPath = "test.runner.json";
        }

        TestSuite rawTestSuite = new ResourceConfigProvider(testSuiteConfigurationPath).getTestSuite();
        TestSuite testSuite = new ParamsMapper().map(rawTestSuite);

       TestRunner testRunner =   new TestRunnerBuilder()
                .addReporter(new SoutReporter())
//                .addReporter(new HtmlReporter("../../resources/", "template.ftl"))
                .build();

       testRunner
               .run(testSuite)
               .report();
    }
}