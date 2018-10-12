package pl.pg.client.rest;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import pl.pg.exception.CannotBuildSSLContextException;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class RestClientBuilder {

    private RestClient restClient;
    private HttpClientBuilder httpClientBuilder;

    public RestClientBuilder() {
        this.httpClientBuilder = HttpClientBuilder.create();
        restClient = new RestClient();
    }

    public RestClient build() {
        restClient.setHttpClient(httpClientBuilder.build());
        return restClient;
    }

    public RestClientBuilder withProxy(ProxyConfig proxyConfig) {
        httpClientBuilder.setRoutePlanner(
                new DefaultProxyRoutePlanner(
                        null,
                        proxyConfig.getScheme(),
                        proxyConfig.getHostname(),
                        proxyConfig.getNonProxyHosts(),
                        proxyConfig.getPort()
                ));
        return this;
    }

    public RestClientBuilder withCookieStore() {
        httpClientBuilder.setDefaultCookieStore(new BasicCookieStore());
        return this;
    }

    public RestClientBuilder withBasicAuth(String url, String username, String password, String method) {
        restClient.setBasicAuth(
                new HttpRequestWithBody(HttpMethod.valueOf(method), url)
                    .basicAuth(username, password));
        return this;
    }

    public RestClientBuilder withAuthentication(String url, Map<String, Object> parameters, HttpMethod method) {
        restClient.setWebSSOAuthentication(
                new HttpRequestWithBody(method, url)
                    .fields(parameters));
        return this;
    }

    public RestClientBuilder withWebSSOAuthentication(AuthenticationConfig authenticationConfig) {
        withCookieStore();
        return withAuthentication(
                authenticationConfig.getUrl(),
                authenticationConfig.getParameters(),
                HttpMethod.valueOf(authenticationConfig.getHttpMethod()));
    }

    public RestClientBuilder withTrustingAllCerts() {
        httpClientBuilder
                .setSSLContext(buildSSLContext())
                .setSSLHostnameVerifier(new NoopHostnameVerifier());
        return this;
    }

    private SSLContext buildSSLContext() {
        try {
            return new SSLContextBuilder().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new CannotBuildSSLContextException(e);
        }
    }

}
