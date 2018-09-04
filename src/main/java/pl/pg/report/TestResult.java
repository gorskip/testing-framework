package pl.pg.report;

import pl.pg.config.TestCase;
import lombok.Data;

@Data
public class TestResult {

    private Status status;
    private TestCase testCase;
    private String failureMessage;


}
