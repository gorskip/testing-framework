package pl.pg.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.apache.commons.collections4.IteratorUtils;
import pl.pg.config.db.Db;
import pl.pg.config.rest.Rest;
import pl.pg.json.JsonMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoryDeserializer extends StdDeserializer<Story> {

    protected StoryDeserializer() {
        this(null);
    }

    public StoryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Story deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode storyNode = jsonParser.getCodec().readTree(jsonParser);
        String storyName = storyNode.get("name").asText();
        List<Map.Entry<String, JsonNode>> storyParams  = getParams(storyNode);

        Story story = new Story();
        story.setName(storyName);

        List<JsonNode> testsNode = IteratorUtils.toList(storyNode.get("tests").elements());
        List<TestCase> testCases = deserilizeTests(testsNode);


        return null;
    }

    private List<TestCase> deserilizeTests(List<JsonNode> testsNode) {
        return testsNode.stream()
                .map(test -> deserilizeTest(test))
                .collect(Collectors.toList());
    }

    private TestCase deserilizeTest(JsonNode testNode) {
        TestCase testCase = new TestCase();

        List<Map.Entry<String, JsonNode>> testParams = getParams(testNode);
        String name = testNode.get("name").asText();
        String tag = testNode.get("tag").asText();
        JsonNode restNode = testNode.get("rest");
        JsonNode dbNode = testNode.get("db");

        Rest rest = deserializeRest(restNode);
        Db db = deserializeDb(dbNode);


        //TODO: testCase.setParams();
        testCase.setName(name);
        testCase.setTag(tag);
        testCase.setRest(rest);
        testCase.setDb(db);
        return testCase;
    }

    private Rest deserializeRest(JsonNode restNode) {
        JsonNode requestNode = restNode.get("request");
        JsonNode expectedNode = restNode.get("expected");



    }

    private void deserializeRequest(JsonNode requestNode) {
        String method = requestNode.get("method").asText();
        String url = requestNode.get("url").asText();
        JsonNode bodyNode = requestNode.get("body");

    }

    private void deserializeExpected(JsonNode expectedNode) {

    }

    private Db deserializeDb(JsonNode dbNode) {
        Db db = new Db();
        List<Map.Entry<String, JsonNode>> testParams = getParams(dbNode);
        //TODO: db.setParams();
        String query = dbNode.get("query").asText();
        db.setQuery(query);
        return db;
    }

    private Object deserializeJsonObject(JsonNode jsonNode) {
        return null;
    }

    private List<Map.Entry<String, JsonNode>> getParams(JsonNode jsonNode) {
        return IteratorUtils.toList(jsonNode.get("params").fields());
    }


}
