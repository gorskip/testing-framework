package pl.pg.client.rest;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import pl.pg.json.JsonMapper;

import java.util.List;

public class RestResponse<T> implements Response {

    private final Class typeParameterClass;
    private final HttpResponse<JsonNode> response;

    public RestResponse(HttpResponse<JsonNode> response, Class typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
        this.response = response;
    }

    public int getStatus() {
        return response.getStatus();
    }

    public T getBody() {
        JsonNode body = response.getBody();
        if(body.isArray()) {
            return (T) JsonMapper.fromJsonToListObject(body.toString(), typeParameterClass);
        }
        return (T) JsonMapper.fromJson(body.getObject().toString(), typeParameterClass);
    }

    public JsonNode getRawBody() {
        return response.getBody();
    }


    public Headers getHeaders() {
        return response.getHeaders();
    }

    @Override
    public List getBodyAsList() {
        return (List<T>) getBody();
    }
}
