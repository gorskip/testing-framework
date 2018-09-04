package config;

import config.rest.Rest;
import config.db.Db;
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
