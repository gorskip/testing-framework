package pl.pg.client.mapper;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import pl.pg.json.JsonMapper;

public class RestResponse<T> implements Response<T> {

    private final Class<T> typeParameterClass;
    private final HttpResponse<JsonNode> response;

    public RestResponse(HttpResponse<JsonNode> response, Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.response = response;
    }

    public int getStatus() {
        return response.getStatus();
    }

    public T getBody() {
        if(response.getBody().isArray()) {
            return JsonMapper.fromJsonToListObject(response.getBody().getArray().toString());
        }
        return JsonMapper.fromJson(response.getBody().getObject().toString(), typeParameterClass);
    }

    public Headers getHeaders() {
        return response.getHeaders();
    }
}
