package pl.pg.db;

import org.junit.Test;
import pl.pg.AbstractTest;
import pl.pg.client.DbClient;
import pl.pg.mapper.DefaultMapper;

import java.util.List;

public class QueryTest extends AbstractTest {

    @Test
    public void Should_Verify_List_Body_Response_Without_Order() {
        DbClient dbClient = new DbClient(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/company",
                "postgres",
                "admin");

        List employees = dbClient.getJdbcTemplate().query("select * from employee", new DefaultMapper(TestEmployee.class));

    }
}
