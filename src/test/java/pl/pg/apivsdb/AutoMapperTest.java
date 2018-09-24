package pl.pg.apivsdb;

import org.junit.Test;
import pl.pg.client.DbResponse;
import pl.pg.client.RestClient;
import pl.pg.client.RestClientBuilder;
import pl.pg.client.entity.Employee;
import pl.pg.client.mapper.RestResponse;
import pl.pg.config.rest.Request;

import java.sql.SQLException;
import java.util.List;

public class AutoMapperTest extends ApiVsDbTest {

    @Test
    public void Should_map_REST_and_DB_results() throws SQLException {

        String query = "select * from employee";
//        List<Employee> dbResponse = dbClient.query(query, Employee.class);

        DbResponse<Employee> dbResponse = dbClient.execute(query, Employee.class);
        System.out.println(dbResponse.getRecords());

        RestClient restClient = new RestClientBuilder().build();

        Request request = new Request();
        request.setUrl("http://localhost:8090/employee");
        request.setMethod("GET");
        RestResponse<List<Employee>> response = restClient.execute(request);


//        List<Employee> employee = JsonMapper.fromJsonToList(response.getBody(), Employee.class);
        System.out.println(response.getBody());

    }
}
