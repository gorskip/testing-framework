package pl.pg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flipkart.zjsonpatch.JsonDiff;
import pl.pg.exception.DeserializationException;
import pl.pg.exception.SerializationException;

import java.io.IOException;
import java.util.List;

public class JsonMapper {

    public static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new DeserializationException("Cannot deserialize: " + json, e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new DeserializationException("Cannot deserialize: " + json, e);
        }
    }

    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Cannot serialize: " + object.toString(), e);
        }
    }

    public static <T> T toObject(Object object, Class<T> clazz) {
        return fromJson(toJson(object), clazz);
    }

    public static JsonNode readTree(String json) {
        try {
           return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new DeserializationException("Cannot deserialize: " + json, e);
        }
    }

    public static JsonNode getDiff(Object expected, Object actual) {
        JsonNode expectedNode = readTree(JsonMapper.toJson(expected));
        JsonNode actualNode = readTree(JsonMapper.toJson(actual));
        JsonNode patchNode = JsonDiff.asJson(expectedNode, actualNode);
        return patchNode;
    }

    public static <T> T fromJsonToListObject(String json) {
        try {
            return MAPPER.readValue(
                    json,
                    MAPPER.getTypeFactory().constructParametricType(List.class, Object.class)
            );
        } catch (IOException e) {
            throw new DeserializationException("Cannot deserialize: " + json, e);
        }
    }

}
