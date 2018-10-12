package pl.pg.client.rest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProxyConfig {

    private String hostname;
    private int port;
    private String[] nonProxyHosts;
    @Builder.Default String scheme = "http";

}
