package config;

import config.db.Db;
import config.rest.Rest;
import json.JsonMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Collectors;

public class ParamsMapper {

    public TestSuite map(TestSuite testSuite) {
        Map<String, Object> suiteParams = testSuite.getParams();
        testSuite.setTests(testSuite.getTests().stream().map(test -> {
            test.setRest(fillParamValues(test.getRest()));
            test.setDb(fillDbParams(test.getDb()));
            test =  fillTestParams(test, suiteParams);
            return test;

        }).collect(Collectors.toList()));
        return testSuite;
    }

    private Rest fillParamValues(Rest rest) {
        if(hasApiParams(rest)) {
            String apiJson = JsonMapper.toJson(rest);
            return JsonMapper.fromJson(fillParams(apiJson, rest.getParams()), Rest.class);
        }
        return rest;
    }

    private Db fillDbParams(Db db) {
        if(hasDbParams(db)) {
            String dbJson = JsonMapper.toJson(db);
            return JsonMapper.fromJson(fillParams(dbJson, db.getParams()), Db.class);
        }
        return db;
    }

    private TestCase fillTestParams(TestCase testCase, Map<String, Object> suiteParams) {
        String jsonTest = JsonMapper.toJson(testCase);
        if(hasTestParams(testCase)) {
            jsonTest = fillParams(jsonTest, testCase.getParams());

        }
        if(suiteParams != null) {
            jsonTest = fillParams(jsonTest,  suiteParams);
        }
        return JsonMapper.fromJson(jsonTest, TestCase.class);

    }

    private String fillParams(String json, Map<String, Object> params) {
        for(Map.Entry<String, Object> entry: params.entrySet()) {
            json =  StringUtils
                    .replaceAll(json, ":".concat(entry.getKey()), entry.getValue().toString());
        }
        return json;
    }

    private boolean hasTestParams(TestCase testCase) {
        return testCase.getParams() != null;
    }

    private boolean hasApiParams(Rest rest) {
        return rest != null && rest.getParams() != null;
    }

    private boolean hasDbParams(Db db) {
        return db !=null && db.getParams() != null;
    }
}
