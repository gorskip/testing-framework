package run;

import cli.CliOptions;
import config.FileProvider;
import config.ParamsMapper;
import config.TestSuite;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import report.SoutTestListener;

public class Main {


    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(CliOptions.getOptions(), args);

        if(cmd.hasOption("help")) {
            CliOptions.printHelp();
            return;
        }

        String storyFile = cmd.getOptionValue("story");
        runTests(storyFile);

    }

    private static void runTests(String storyFile) {
        TestSuite rawTestSuite = new FileProvider(storyFile).getTestSuite();
        TestSuite testSuite = new ParamsMapper().map(rawTestSuite);
        TestRunner testRunner =   new TestRunnerBuilder()
                .addReporter(new SoutTestListener())
                .build();
        testRunner
                .run(testSuite)
                .report();
    }
}