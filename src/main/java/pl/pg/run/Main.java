package run;

import cli.CliOptions;
import config.FileProvider;
import config.ParamsMapper;
import config.Story;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import report.HtmlTestListener;
import report.SoutTestListener;
import util.ResourceUtil;

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
        Story rawStory = new FileProvider(storyFile).getStory();
        Story story = new ParamsMapper().map(rawStory);
        TestRunner testRunner =   new TestRunnerBuilder()
                .addReporter(new SoutTestListener())
                .addReporter(new HtmlTestListener(new ResourceUtil().getFile("/test.runner.json")))
                .build();
        testRunner
                .run(story)
                .report();
    }
}