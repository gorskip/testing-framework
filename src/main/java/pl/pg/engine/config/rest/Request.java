package pl.pg.config.rest;

import lombok.Data;

import java.util.Map;

@Data
public class Request {

    private String method;
    private String url;
    private Map<String, String> headers;
    private Object body;


}
