package pl.pg.engine.apivsdb;

import pl.pg.client.db.DbClient;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;

public class ApiVsDbTest {

    protected final DbClient dbClient;
    protected final RestClient restClient;

    public ApiVsDbTest() {
        this.dbClient = new DbClient(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/company",
                "postgres",
                "admin");
        this.restClient = new RestClientBuilder().build();
    }
}
