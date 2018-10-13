package pl.pg.run;

import org.apache.commons.cli.ParseException;
import pl.pg.engine.config.FileProvider;
import pl.pg.engine.config.DefaultParamsMapper;
import pl.pg.engine.config.Story;
import pl.pg.report.HtmlTestListener;
import pl.pg.report.SoutTestListener;

import java.io.File;

public class Main {


    public static void main(String[] args) throws ParseException {
//        System.out.print(args.length);
//        CommandLineParser parser = new DefaultParser();
//        CommandLine cmd = parser.parse(CliOptions.getOptions(), args);
//
//        if(cmd.hasOption("help")) {
//            CliOptions.printHelp();
//            return;
//        }

//        String storyFile = cmd.getOptionValue("stories");
        String storyFile = "C:\\development\\api-db-test-framework\\test-suite.json";
        runTests(storyFile);

    }

    private static void runTests(String storyFile) {
        String template = "C:\\development\\repositories\\testing-framework\\template.ftl";

        Story rawStory = new FileProvider(storyFile).getStory();
        Story story = new DefaultParamsMapper().map(rawStory);
        TestRunner testRunner =   new TestRunnerBuilder()
                .addReporter(new SoutTestListener())
                .addReporter(new HtmlTestListener(new File(template)))
                .build();
        testRunner
                .run(story)
                .report();
    }
}