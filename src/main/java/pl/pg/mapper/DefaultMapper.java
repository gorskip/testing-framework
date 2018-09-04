package mapper;

import annotation.Column;
import annotation.Mapper;
import exception.CannotCreateNewInstanceException;
import exception.CannotMapColumn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(name = "default")
public class DefaultMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public DefaultMapper(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        T instance = getNewInstance();
        getSourceFields().stream()
                .forEach(sourceField -> {
                    mapField(instance, resultSet, i, sourceField);
                });
        return instance;
    }

    private void mapField(T instance, ResultSet resultSet, int i, SourceField sourceField) {
        String columnName = getColumnName(sourceField);
        try {
            mapWithColumnType(instance, resultSet, i, columnName, sourceField.getField());
        } catch (SQLException | IllegalAccessException e) {
           throw new CannotMapColumn("Cannot map column: [" + columnName + "]", e);
        }
    }

//    TODO: Add other types
    private void mapWithColumnType(T instance, ResultSet resultSet, int i, String columnName, Field field) throws SQLException, IllegalAccessException {
        int type = resultSet.getMetaData().getColumnType(i);
        field.setAccessible(true);
        switch(type) {
//            case Types.VARCHAR: field.set(instance, resultSet.getString(columnName)); break;
            default: field.set(instance, resultSet.getObject(columnName));
         }
    }

    private String getColumnName(SourceField sourceField) {
        String columnName = sourceField.getColumn().name();
        if(StringUtils.isNotEmpty(columnName)) {
            return columnName;
        }
        return sourceField.getField().getName();
    }

    private List<SourceField> getSourceFields() {
        return Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotationsByType(Column.class) != null)
                .map(field -> new SourceField(field, field.getAnnotation(Column.class)))
                .collect(Collectors.toList());
    }

    private T getNewInstance() {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new CannotCreateNewInstanceException("For class: " + clazz.getName(), e);
        }
    }

}
