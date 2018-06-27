package run;

import cmd.Cmd;
import config.FileProvider;
import config.ParamsMapper;
import config.TestSuite;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import report.HtmlTestListener;
import report.TestListener;
import report.SoutTestListener;

import java.util.*;

public class Main {


    public static void main(String[] args) throws ParseException {

        CommandLine commandLine = Cmd.getCommandLine(args);

        String testSuitePath = commandLine.getOptionValue("ts");
        String[] reporters = commandLine.getOptionValues("r");

        TestSuite rawTestSuite = new FileProvider(testSuitePath).getTestSuite();
        TestSuite testSuite = new ParamsMapper().map(rawTestSuite);

       TestRunner testRunner =   new TestRunnerBuilder()
               .withTestListeners(new ArrayList<>(getReporters(reporters)))
                .build();

       testRunner
               .run(testSuite)
               .report();
    }

    public static Set<TestListener> getReporters(String[] commandReporters) {
        Set<TestListener> testListeners = new HashSet<>();
        if(commandReporters != null) {
            Arrays.asList(commandReporters).forEach(reporter -> {
                switch(reporter.toLowerCase()) {
                    case "sout": testListeners.add(new SoutTestListener());
                        break;
                    case "html": testListeners.add(new HtmlTestListener("../../resources/", "template.ftl"));
                        break;
                }
            });
        }
        return testListeners;
    }
}