package pl.pg.config.mock;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.JsonNode;
import lombok.Data;
import pl.pg.client.rest.Response;

import java.util.List;

@Data
public class MockResponse implements Response {

    private int status;
    private Object body;

    @Override
    public JsonNode getRawBody() {
        return null;
    }

    @Override
    public Headers getHeaders() {
        return null;
    }

    @Override
    public List getBodyAsList() {
        return null;
    }
//    private Map<String, String> headers;
}
