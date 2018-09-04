package config.db;

import lombok.Data;

import java.util.Map;

@Data
public class Db {

    private String query;
    private Map<String, Object> params;
}
