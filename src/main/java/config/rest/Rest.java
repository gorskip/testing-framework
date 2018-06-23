package config.rest;

import lombok.Data;

import java.util.Map;

@Data
public class Rest {

    private Map<String, Object> params;
    private Request request;
    private Expected expected;

}
