package pl.pg.demo.util;

import pl.pg.StoryTest;
import pl.pg.annotation.Story;
import pl.pg.annotation.Test;
import pl.pg.demo.util.model.Employee;
import pl.pg.engine.config.TestCase;

import java.sql.SQLException;

@Story("Employee module story")
public class EmployeeStoryTest extends StoryTest {

    @Test("Get employees")
    public void getEmployeesTest(TestCase testCase) throws SQLException {
        run(testCase, Employee.class)
                .and()
                .verifyHttpStatus()
                .verifyThatBodyContainsExpected()
                .verifyThatRestAndDbResponsesAreTheSame()
                .validateAll();
    }
}
