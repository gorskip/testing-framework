package pl.pg.engine.config;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import pl.pg.engine.config.db.Db;
import pl.pg.engine.config.rest.Expected;
import pl.pg.engine.config.rest.Request;
import pl.pg.engine.config.rest.Rest;
import pl.pg.json.JsonMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParamsMapper implements IParamsMapper {

    @Override
    public Story map(Story story) {
        Map<String, Object> storyParams = story.getParams();
        story.setTests(story.getTests().stream()
                .map(test -> parametrizeTest(test, storyParams))
                .collect(Collectors.toList()));
        return story;
    }

    private TestCase parametrizeTest(TestCase test, Map<String, Object> suiteParams) {
        suiteParams.entrySet().stream()
                .forEach(entry -> test.getParams().putIfAbsent(entry.getKey(), entry.getValue()));
        parametrizeRest(test);
        parametrizeDb(test);
        return test;
        
    }

    private void parametrizeRest(TestCase test) {
        Rest rest = test.getRest();
        if(rest != null) {
            Map<String, Object> params = test.getParams();
            parametrizeRequest(rest, params);
            parametrizeExpected(rest, params);
            test.setRest(rest);
        }
    }

    private void parametrizeDb(TestCase test) {
        Db db = test.getDb();
        if(db != null) {
            String query = parametrizeText(db.getQuery(), test.getParams());
            db.setQuery(query);
            test.setDb(db);
        }
    }

    private void parametrizeRequest(Rest rest, Map<String, Object> params){
        Request request = rest.getRequest();
        if(request != null) {
            String url = parametrizeText(request.getUrl(), params);
            String method = parametrizeText(request.getMethod(), params);
            JsonNode bodyNode = JsonMapper.convert(request.getBody());
            parametrizeRequestBody(bodyNode, params);
            request.setBody(JsonMapper.toObject(bodyNode, Object.class));
            request.setUrl(url);
            request.setMethod(method);
            rest.setRequest(request);
        }
    }

    private void parametrizeExpected(Rest rest, Map<String, Object> params) {
        Expected expected = rest.getExpected();
        if(expected != null) {
            JsonNode bodyNode = JsonMapper.convert(expected.getBody());
            parametrizeRequestBody(bodyNode, params);
            expected.setBody(JsonMapper.toObject(bodyNode, Object.class));
            rest.setExpected(expected);
        }
    }

    private void parametrizeRequestBody(JsonNode bodyNode, Map<String, Object> params) {
        List<Map.Entry<String, JsonNode>> paramEntries = prepareParams(params);
        Iterator<Map.Entry<String, JsonNode>> fields = bodyNode.fields();
        List<Map.Entry<String, JsonNode>> bodyFields = IteratorUtils.toList(fields);

        bodyFields.stream().forEach(node -> System.out.println(node));

        for(Map.Entry<String, JsonNode> bodyField: bodyFields) {
            JsonNode value = bodyField.getValue();
            for(Map.Entry<String, JsonNode> entry: paramEntries) {
                if(":".concat(entry.getKey()).equals(value.asText())) {
                    value = JsonMapper.convert(entry.getValue());
                }else {
                    if(value.asText().contains(":".concat(entry.getKey()))){
                        String replacement = entry.getValue().toString();
                        String newValue = value.asText().replaceAll(":".concat(entry.getKey()), replacement.replaceAll("\"", ""));
                        value = JsonMapper.convert(newValue);
                    }
                }
            }
            bodyField.setValue(value);
        }
    }

    private String parametrizeText(final String text, Map<String, Object> params) {
        String parametrizedValue = text;
        List<Map.Entry<String, JsonNode>> entries = prepareParams(params);
        for(Map.Entry<String, JsonNode> param: entries) {
            String key = param.getKey();
            JsonNode value = param.getValue();
            if(":".concat(key).equals(parametrizedValue)) {
                return value.asText();
            }
            if(text.contains(key)) {
                parametrizedValue  = StringUtils.replaceAll(parametrizedValue, ":".concat(key), value.asText());
            }
        }
        return parametrizedValue;
    }

    private List<Map.Entry<String, JsonNode>> prepareParams(Map<String, Object> params) {
        JsonNode jsonNode = JsonMapper.MAPPER.convertValue(params, JsonNode.class);
        List<Map.Entry<String, JsonNode>> entries = IteratorUtils.toList(jsonNode.fields());
        return entries;
    }
}
