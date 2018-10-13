package pl.pg.engine.config;

import pl.pg.engine.config.rest.Rest;
import pl.pg.engine.config.db.Db;
import lombok.Data;

import java.util.Map;

@Data
public class TestCase {

    private String name;
    private String tag;
    private Map<String, Object> params;
    private Rest rest;
    private Db db;
    private String mapper;


}
