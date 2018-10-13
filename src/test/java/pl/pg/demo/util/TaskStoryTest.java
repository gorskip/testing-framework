package pl.pg.demo.util;

import pl.pg.StoryTest;
import pl.pg.annotation.Story;
import pl.pg.annotation.Test;
import pl.pg.client.rest.Response;
import pl.pg.demo.util.model.Task;
import pl.pg.engine.config.TestCase;
import pl.pg.engine.config.db.Db;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Request;
import pl.pg.engine.config.rest.Rest;

import java.sql.SQLException;
import java.util.List;

@Story("Task story")
public class TaskStoryTest extends StoryTest {

    @Test("Task test")
    public void test(TestCase testCase) throws SQLException {
        Rest rest = testCase.getRest();

        Request request = rest.getRequest();
        Expected expected = rest.getExpected();

        Response<List<Task>> response = restClient().execute(request, Task.class);

        verifyIf().given(response)
                .has(expected)
                .status();

        Db db = testCase.getDb();

        List<Task> dbResponse = dbClient().query(db.getQuery(), Task.class);

        assert dbResponse.equals(response.getBody());



    }
}
