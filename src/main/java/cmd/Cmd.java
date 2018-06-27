package cmd;

import org.apache.commons.cli.*;

public class Cmd {

    public static CommandLine getCommandLine(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("ts", "testSuite", true, "Test Suite File");
        Option option = new Option("r", "reporter", false, "List of reporters");
        option.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(option);

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Test Framework", options);

        CommandLineParser parser = new DefaultParser();

        return parser.parse(options, args);
    }
}
