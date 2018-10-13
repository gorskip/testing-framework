package pl.pg.apivsdb;

import pl.pg.StoryTest;
import pl.pg.annotation.Story;
import pl.pg.annotation.Test;
import pl.pg.config.TestCase;

import java.sql.SQLException;

@Story("Employee module")
public class EmployeeTest extends StoryTest {

    @Test("Get all employees")
    public void getEmployeesTest(TestCase testCase) throws SQLException {


        //verify(testCase, Employee.class) { =>

        //prepare data for rest request
//        Rest rest = testCase.getRest();
//        Request request = rest.getRequest();
//
//        //prepare expected data
//        Expected expected = rest.getExpected();
//
//        //execute request and get response
//        Response restResponse = restClient().execute(request, Employee.class);
//
//        System.out.println(restResponse.getBodyAsList().get(0));
//
////prepare data for db request
//        Db db = testCase.getDb();
//        String sql = db.getQuery();
//
//        //execute sql query and get results
//        List<Employee> queryResult = dbClient().query(sql, Employee.class);
//
//        System.out.println(queryResult);
//
//        Arrays.deepEquals(restResponse.getBodyAsList().toArray(), queryResult.toArray());
//
//
//
////        //verify if giver response is as expected
//        verifyIf().given(restResponse)
//                .contains(expected)
////                .body()
//                .and()
//                .equalsQueryResult(queryResult);

//                .and(expected)
//                .body()
//                .headers();

        //prepare data for db request
//        Db db = testCase.getDb();
//        String sql = db.getQuery();
//
//        //execute sql query and get results
//        List<Employee> queryResult = dbClient().query(sql, Employee.class);

        //verify if rest response and databse response are the same
        //uses Arrays.deepEquals

//        assert queryResult.equals(restResponse.getBodyAsList());


        //if Employee class has Validator validate entity fields
//        validate(restResponse);
//        validate(queryResult);
    }
}
