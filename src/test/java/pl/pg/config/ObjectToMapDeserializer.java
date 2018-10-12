package pl.pg.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ObjectToMapDeserializer extends StdDeserializer<ObjectToMap> {

    protected ObjectToMapDeserializer() {
        this(null);
    }

    public ObjectToMapDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public ObjectToMap deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String value = node.get("value").asText();
        JsonNode jsonNode = node.get("object");

        ObjectToMap objectToMap = new ObjectToMap();
        objectToMap.setValue(value);
        objectToMap.setObject(jsonNode);
        return objectToMap;
    }
}
