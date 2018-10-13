package pl.pg.engine.config;


import pl.pg.StoryTest;
import pl.pg.annotation.Story;
import pl.pg.annotation.Test;
import pl.pg.client.entity.Employee;
import pl.pg.engine.config.db.Db;

import java.sql.SQLException;
import java.util.List;

@Story("DbConfig")
public class DbConfigTest extends StoryTest {

    @Test("Query test with dbConfig")
    public void test(TestCase testCase) throws SQLException {

        Db db = testCase.getDb();

        List<Employee> employees = dbClient().query(db.getQuery(), Employee.class);
        System.out.println(employees);
        assert !employees.isEmpty();
    }

}
