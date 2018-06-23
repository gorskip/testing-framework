package client;

import client.mapper.Response;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import config.rest.Request;
import exception.RequestException;
import json.JsonMapper;
import org.apache.http.client.HttpClient;

public class RestClient {


    public RestClient(HttpClient httpClient) {
        Unirest.setHttpClient(httpClient);
    }

    public Response execute(Request request) {
        return execute(request, Object.class);
    }

    public <T> Response<T> execute(Request request, Class<T> clazz) {
        switch (request.getMethod()) {
            case "GET":
                return get(request, clazz);
            case "POST":
                return post(request, clazz);
        }
        //TODO: zwroc cos sensownego, moze jakis fancy obiekt
        return null;
    }

    private <T> Response<T> post(Request request, Class<T> clazz) {
        try {
            return new Response<>(executePost(request), clazz);
        } catch (UnirestException e) {
            throw new RequestException("Cannot execute request: " + request, e);
        }
    }

    private HttpResponse<JsonNode> executePost(Request request) throws UnirestException {
        return Unirest.post(request.getUrl())
                .headers(request.getHeaders())
                .body(JsonMapper.toJson(request.getBody()))
                .asJson();
    }

    private <T> Response<T> get(Request request, Class<T> clazz) {
        try {
            return new Response<>(executeGet(request), clazz);
        } catch (UnirestException e) {
            throw new RequestException("Cannot execute request: " + request, e);
        }
    }

    private HttpResponse<JsonNode> executeGet(Request request) throws UnirestException {
        HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get(request.getUrl())
                .headers(request.getHeaders())
                .asJson();
        return jsonNodeHttpResponse;
    }
}
