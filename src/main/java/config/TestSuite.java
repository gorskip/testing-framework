package config;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TestSuite {

    String name;
    Map<String, Object> params;
    List<TestCase> tests;
}
