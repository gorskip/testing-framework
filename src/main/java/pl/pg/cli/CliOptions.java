package pl.pg.cli;

import lombok.Data;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

@Data
public class CliOptions {

    public static Options getOptions() {
        Options options = new Options();
        options.addOption(
                Option.builder("story")
                        .hasArg()
                        .required(false)
                        .desc("Path to story configuration file")
                        .longOpt("story")
                        .build());
        options.addOption(
                Option.builder("h")
                        .hasArg(false)
                        .desc("Prints usage of parameters")
                        .argName("h")
                        .longOpt("help")
                        .build());
        return options;
    }

    public static void printHelp() {
        new HelpFormatter().printHelp("Testing framework", getOptions());
    }
}
