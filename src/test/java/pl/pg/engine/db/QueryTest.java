package pl.pg.engine.db;

import org.junit.Test;
import pl.pg.engine.AbstractTest;
import pl.pg.client.db.DbClient;

public class QueryTest extends AbstractTest {

    @Test
    public void Should_Verify_List_Body_Response_Without_Order() {
        DbClient dbClient = new DbClient(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/company",
                "postgres",
                "admin");

//        List employees = dbClient.getJdbcTemplate().query("select * from employee", new DefaultMapper(TestEmployee.class));
        throw new RuntimeException();

    }
}
