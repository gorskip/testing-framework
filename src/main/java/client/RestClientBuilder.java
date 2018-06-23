package client;

import org.apache.http.impl.client.HttpClientBuilder;

public class RestClientBuilder {

    private RestClient restClient;
    private HttpClientBuilder builder;

    public RestClientBuilder() {
        this.builder = HttpClientBuilder.create();
        this.restClient = new RestClient(builder.build());

    }
    public RestClient build() {
        return restClient;
    }
}
