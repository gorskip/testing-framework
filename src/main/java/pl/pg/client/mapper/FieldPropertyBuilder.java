package pl.pg.client.mapper;

import org.apache.commons.lang3.StringUtils;
import pl.pg.annotation.Column;

import java.lang.reflect.Field;

public class FieldPropertyBuilder {

    public FieldProperty build(Field field) {
        Column column = field.getAnnotation(Column.class);
        if(column == null) {
            return new FieldProperty(field, field.getName(), field.getName(), null);
        }else {
            String columnName = ((StringUtils.isEmpty(column.name())) ? field.getName() : column.name());
            Class mapper = ((column.mapper() == void.class) ? null : column.mapper());
            return new FieldProperty(
                    field,
                    field.getName(),
                    columnName,
                    mapper
                );
        }
    }
}
