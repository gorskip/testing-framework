package pl.pg.engine.config.rest;

import pl.pg.json.JsonMapper;
import lombok.Data;

import java.util.Map;

@Data
public class Expected {


    private Integer status;
    private Object body;
    private Map<String, String> headers;
    private Type type;

    public <T> T getBodyAs(Class<T> clazz) {
        return JsonMapper.toObject(this.body, clazz);
    }

}
