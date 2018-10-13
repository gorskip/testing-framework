package pl.pg.config;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Story {

    String name;
    Map<String, Object> params;
    List<TestCase> tests;
    String dbConfig;
}
