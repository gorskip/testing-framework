package pl.pg.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flipkart.zjsonpatch.JsonDiff;
import pl.pg.exception.DeserializationException;
import pl.pg.exception.SerializationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonMapper {

    public static ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JavaTimeModule())
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

    public static <T> List<T> fromJsonToList(Object json, Class<T> clazz) {
        return (List<T>) fromJsonToListObject(toJson(json), clazz);
    }

    public static List fromJsonToListObject(String json, Class clazz) {
        try {
            JavaType valueType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            System.out.println((valueType.getTypeName()));
            return MAPPER.readValue(
                    json,
                    valueType);
        } catch (IOException e) {
            throw new DeserializationException("Cannot deserialize: " + json, e);
        }
    }

    public static JsonNode convert(Object object) {
        return MAPPER.valueToTree(object);
    }

}
