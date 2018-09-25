package pl.pg.client.mapper;

import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;

@Data
@ToString
public class FieldProperty {

    private Field field;
    private String fieldName;
    private String columnName;
    private Class mapper;


    public FieldProperty(Field field, String fieldName, String columnName, Class<? extends FieldMapper> mapper) {
        this.field = field;
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.mapper = mapper;
    }
}
