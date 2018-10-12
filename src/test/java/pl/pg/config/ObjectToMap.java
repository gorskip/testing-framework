package pl.pg.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonDeserialize(using = ObjectToMapDeserializer.class)
public class ObjectToMap {

    String value;
    JsonNode object;
}
