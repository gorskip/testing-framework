package pl.pg.client.mapper;

import org.apache.commons.dbutils.ResultSetHandler;
import pl.pg.annotation.Mapper;
import pl.pg.util.InstanceUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultResultSetHandler<T> implements ResultSetHandler<List<T>> {

    private final Map<String, FieldProperty> fieldsMap;
    private final Class<T> clazz;

    public DefaultResultSetHandler(Class<T> clazz) {
        this.clazz = clazz;
        this.fieldsMap = buildFieldsMap();
    }

    @Override
    public List<T> handle(ResultSet resultSet) throws SQLException {
        if(clazz.isAnnotationPresent(Mapper.class)) {
            return handleWithClassMapper(resultSet);
        }
        return handleWithFieldMapper(resultSet);
    }

    private List<T> handleWithClassMapper(ResultSet resultSet) throws SQLException {
        Mapper annotation = clazz.getAnnotation(Mapper.class);
        Class<? extends EntityMapper> mapperClass = annotation.value();
        EntityMapper mapper = (EntityMapper) InstanceUtil.createInstanceOf(mapperClass);
        return mapper.handle(resultSet);
    }

    private List<T> handleWithFieldMapper(ResultSet resultSet) throws SQLException {
        List<T> results = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        while(resultSet.next()) {
            T instance = (T) InstanceUtil.createInstanceOf(clazz);
            int numColumns = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                String column_name = resultSetMetaData.getColumnName(i);
                FieldProperty fieldProperty = fieldsMap.get(column_name);
                Field field = fieldProperty.getField();
                field.setAccessible(true);
                try {
                    field.set(instance, prepareValue(resultSet.getObject(column_name), fieldProperty.getMapper()));
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException("Cannot map field with properties: " + fieldProperty.toString());
                }
            }
            results.add(instance);
        }
        return results;
    }


    private Object prepareValue(Object object, Class<? extends FieldMapper> mapperClass) throws IllegalAccessException, InstantiationException {
        if(mapperClass == null) {
            return object;
        }
        FieldMapper mapper = mapperClass.newInstance();
        return mapper.map(object);
    }

    private Map<String, FieldProperty> buildFieldsMap() {
        Map<String, FieldProperty> map =  new HashMap<>();
        FieldPropertyBuilder builder = new FieldPropertyBuilder();
        for(Field field: clazz.getDeclaredFields()) {
            FieldProperty fieldProperty = builder.build(field);
            String columnName = fieldProperty.getColumnName();
            map.put(columnName, fieldProperty);
            map.put(columnName.toUpperCase(), fieldProperty);
            map.put(columnName.toLowerCase(), fieldProperty);
        }
        return map;
    }
}
