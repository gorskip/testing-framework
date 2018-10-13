package pl.pg.engine.apivsdb;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import pl.pg.client.entity.Task;
import pl.pg.client.rest.Response;
import pl.pg.client.rest.RestClient;
import pl.pg.client.rest.RestClientBuilder;
import pl.pg.engine.config.rest.Request;

import java.sql.SQLException;
import java.util.List;

import static pl.pg.validator.ValidateExecutor.validate;

public class AutoMapperTest extends ApiVsDbTest {

    @Test
    public void Should_map_REST_and_DB_results() throws SQLException {

//        new StoryProcessor().getTestStories();

//        String query = "select * from employee";
//        List<Employee> employees = dbClient.query(query, Employee.class);
//        assert employees.size() == 2;
//
//        DbResponse<Employee> dbResponse = dbClient.execute(query, Employee.class);
//        System.out.println(dbResponse.getRecords());
//
//        RestClient restClient = new RestClientBuilder().build();
//
//        Request request = new Request();
//        request.setUrl("http://localhost:8090/employee");
//        request.setMethod("GET");
//        Response<List<Employee>> response = restClient.execute(request);
//
//
//
//
////        System.out.println(response.getBody());
//
//        response.getBody();


    }

    @Test
    public void Should_map_REST_and_DB_results2() throws SQLException, UnirestException {

        String query = "select * from task";

        List<Task> tasks = dbClient.query(query, Task.class);
        System.out.println(tasks.size());

        System.out.println(tasks);

        RestClient restClient = new RestClientBuilder().build();

        Request request = new Request();
        request.setUrl("http://localhost:8090/task");
        request.setMethod("GET");
        Response response = restClient.execute(request, Task.class);



//        new VerifyIf.VerifyBuilder().build()
//                .given(response2)
//                .has(expected)
//                .body();
////


        validate(tasks);
    }
}
