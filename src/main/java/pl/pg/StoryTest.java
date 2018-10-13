package pl.pg;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.runner.RunWith;
import pl.pg.annotation.Validator;
import pl.pg.client.db.DbClient;
import pl.pg.client.db.DbResponse;
import pl.pg.client.rest.Response;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.engine.config.Config;
import pl.pg.engine.config.DbConfig;
import pl.pg.engine.config.TestCase;
import pl.pg.engine.config.db.Db;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Request;
import pl.pg.engine.config.rest.Rest;
import pl.pg.exception.CannotFindDbConfigurationException;
import pl.pg.exception.CannotReadConfigException;
import pl.pg.json.JsonMapper;
import pl.pg.util.FileUtil;
import pl.pg.verify.VerifyIf;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static pl.pg.validator.ValidateExecutor.validate;

@RunWith(StoryRunner.class)
public class StoryTest {

    protected final JSONObject params = StoryRunner.params;
    private TestCase testCase;
    private Rest rest;
    private Request request;
    private Expected expected;
    private Db db;
    private Response response;
    private List dbResults;



    private DbClient dbClient;
    private RestClient restClient;
    protected static final VerifyIf VERIFY_IF = new VerifyIf.VerifyBuilder().build();
    protected static final VerifyIf VERIFY_IF_WITH_ORDER = new VerifyIf.VerifyBuilder().withOrderChecking().build();

    public StoryTest() {
        this.restClient = new RestClientBuilder().build();
        this.dbClient = prepareDbClient();

    }

    private DbClient prepareDbClient() {
        String dbConfigName = params.getString("dbConfig");
        try {
            Config config = JsonMapper.fromJson(FileUtil.getResourceContent("config.json"), Config.class);
            Optional<DbConfig> optionalConfig = config.getDbConfigs().stream()
                    .filter(dbConfig -> dbConfigName.equals(dbConfig.getName()))
                    .findFirst();
            if(!optionalConfig.isPresent()) {
                throw new CannotFindDbConfigurationException("with name: " + dbConfigName);
            }
            DbConfig dbConfig = optionalConfig.get();
            return new DbClient(dbConfig);
        } catch (IOException e) {
           throw new CannotReadConfigException(e);
        }
    }

    public DbClient dbClient() {
        return dbClient;
    }

    public RestClient restClient() {
        return restClient;
    }

    public VerifyIf verifyIf() {
        return VERIFY_IF;
    }

    public StoryTest run(TestCase testCase, Class model) throws SQLException {
        this.testCase = testCase;
        this.request = testCase.getRest().getRequest();
        this.expected = testCase.getRest().getExpected();
        this.db = testCase.getDb();

        this.response = restClient().execute(request, model);
        this.dbResults = dbClient().query(db.getQuery(), model);
        return this;
    }

    public StoryTest and() {
        return this;
    }

    public StoryTest verifyHttpStatus() {
        newVerifyIf().given(response)
                .has(expected)
                .status();
        return this;
    }

    public StoryTest verifyBodyIsAsExpected() {
        newVerifyIf().given(response)
                .has(expected)
                .body();
        return this;
    }

    public StoryTest verifyThatBodyContainsExpected() {
        newVerifyIf().given(response)
                .contains(expected)
                .body();
        return this;
    }

    public StoryTest verifyThatRestAndDbResponsesAreTheSame() {
        newVerifyIf().given(response)
                .equalsQueryResult(dbResults);
        return this;
    }

    public void validateRestResponse() {
        validate(response);
    }

    public void validateDbResponse() {
        validate(dbResults);
    }

    public void validateAll() {
        validateRestResponse();
        validateDbResponse();
    }

    private VerifyIf newVerifyIf() {
        return new VerifyIf.VerifyBuilder().build();
    }


}
