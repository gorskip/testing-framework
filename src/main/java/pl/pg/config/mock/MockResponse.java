package pl.pg.config.mock;

import pl.pg.client.mapper.Response;
import com.mashape.unirest.http.Headers;
import lombok.Data;

@Data
public class MockResponse implements Response {

    private int status;
    private Object body;

    @Override
    public Headers getHeaders() {
        return null;
    }
//    private Map<String, String> headers;
}
