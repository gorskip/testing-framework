package pl.pg;

import org.json.JSONObject;
import org.junit.runner.RunWith;
import pl.pg.annotation.Validator;
import pl.pg.client.db.DbClient;
import pl.pg.client.rest.Response;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.config.TestCase;
import pl.pg.config.rest.Rest;
import pl.pg.verify.VerifyIf;

import java.sql.SQLException;
import java.util.List;

import static pl.pg.validator.ValidateExecutor.validate;

@RunWith(StoryRunner.class)
public class StoryTest {

    protected final JSONObject params = StoryRunner.params;



    private DbClient dbClient;
    private RestClient restClient;
    protected static final VerifyIf VERIFY_IF = new VerifyIf.VerifyBuilder().build();
    protected static final VerifyIf VERIFY_IF_WITH_ORDER = new VerifyIf.VerifyBuilder().withOrderChecking().build();

    public StoryTest() {
        this.restClient = new RestClientBuilder().build();
        dbClient = new DbClient(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/company",
                "postgres",
                "admin");
    }

    public DbClient dbClient() {
        return dbClient;
    }

    public RestClient restClient() {
        return restClient;
    }

    public StoryTest verify(TestCase testCase, Class clazz) throws SQLException {
        Rest rest = testCase.getRest();
        List dbResponse = dbClient.query(testCase.getDb().getQuery(), clazz);
        Response restResponse = restClient.execute(rest.getRequest(), clazz);

        List responseBody = (List) restResponse.getBody();
        assert dbResponse.size() == responseBody.size();
        //TODO: Add all
        VERIFY_IF.given(restResponse)
                .has(rest.getExpected())
                .status();
        if(clazz.isAnnotationPresent(Validator.class)) {
            validate(dbResponse);
            validate(responseBody);
        }
        return this;
    }

    public VerifyIf verifyIf() {
        return VERIFY_IF;
    }

}
