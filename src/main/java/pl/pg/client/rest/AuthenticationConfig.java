package pl.pg.client.rest;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class AuthenticationConfig {

    private String url;
    private Map<String, Object> parameters;
    private String httpMethod;
}
