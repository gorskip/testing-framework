package pl.pg.config;

import com.fasterxml.jackson.databind.node.JsonNodeType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Param {

    Object value;
    JsonNodeType type;
}
