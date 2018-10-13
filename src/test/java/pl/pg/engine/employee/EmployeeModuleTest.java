package pl.pg.engine.employee;

import pl.pg.StoryTest;
import pl.pg.annotation.Story;
import pl.pg.annotation.Test;
import pl.pg.engine.config.TestCase;

@Story("Employee module stories")
public class EmployeeModuleTest extends StoryTest {

    @Test("Create employee")
    public void createEmployee(TestCase testCase) {

        System.out.println(params);


//        Rest rest = testCase.getRest();
//        Response response = restClient().execute(rest.getRequest());
//        Expected expected = rest.getExpected();
//        verifyIf().given(response)
//                .has(expected)
//                .status();

    }

//    @Test("Read employees")
//    public void readEmployees(TestCase testCase) throws SQLException {
//        Rest rest = testCase.getRest();
//        Request request = rest.getRequest();
//        Expected expected = rest.getExpected();
//
//        Response<List<Employee>> response = restClient().execute(request, Employee.class);
//
//        verifyIf().given(response)
//                .contains(expected)
//                .body()
//                .and()
//                .status();
//
//
//        List<Employee> queryResult = dbClient().query(testCase.getDb().getQuery(), Employee.class);
//
//        verifyIf().given(response)
//                .equalsQueryResult(queryResult);
//
//    }
}
