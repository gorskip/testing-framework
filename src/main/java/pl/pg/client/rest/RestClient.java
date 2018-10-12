package pl.pg.client.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.MultipartBody;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import pl.pg.config.rest.Request;
import pl.pg.exception.RequestException;
import pl.pg.json.JsonMapper;

public class RestClient {

    private HttpClient httpClient;

    public RestClient() {
        initRestClient();
    }

    private void initRestClient() {
        if(httpClient == null) {
            Unirest.setHttpClient(HttpClientBuilder.create().build());
            return;
        }
        Unirest.setHttpClient(httpClient);
    }

    public Response execute(Request request) {
        return execute(request, Object.class);
    }

    public <T> Response<T> execute(Request request, Class clazz) {
        switch (request.getMethod()) {
            case "GET":
                return get(request, clazz);
            case "POST":
                return post(request, clazz);
        }
        //TODO: zwroc cos sensownego, moze jakis fancy obiekt
        return null;
    }

    public void setWebSSOAuthentication(MultipartBody fields) {

    }

    private <T> RestResponse<T> post(Request request, Class<T> clazz) {
        try {
            return new RestResponse<>(executePost(request), clazz);
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

    private <T> RestResponse<T> get(Request request, Class<T> clazz) {
        try {
            return new RestResponse<>(executeGet(request), clazz);
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

    public void setBasicAuth(HttpRequestWithBody basicAuth) {
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
