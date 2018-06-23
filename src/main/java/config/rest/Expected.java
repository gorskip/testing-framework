package config.rest;

import json.JsonMapper;
import lombok.Data;

import java.util.Map;

@Data
public class Expected {

    private int status;
    private Object body;
    private Map<String, String> headers;

    public <T> T getBodyAs(Class<T> clazz) {
        return JsonMapper.toObject(this.body, clazz);
    }

}
