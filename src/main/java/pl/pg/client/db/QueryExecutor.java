package pl.pg.client.db;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import pl.pg.annotation.Column;
import pl.pg.client.mapper.DefaultResultSetHandler;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryExecutor<T> {

    private final QueryRunner queryRunner;

    public QueryExecutor(QueryRunner queryRunner) {
        this.queryRunner = queryRunner;
    }

    public <T> List<T> execute(String query, Class<T> clazz) throws SQLException {
        System.out.println(clazz.getName());
//        ResultSetHandler<List<T>> resultHandler = prepareResultHandler(clazz);
        ResultSetHandler<List<T>> resultHandler = new DefaultResultSetHandler<T>(clazz);
        return queryRunner.query(query, resultHandler);
    }

    public <T> DbResponse<T> getResponse(String query, Class<T> clazz) throws SQLException {
        ResultSetHandler resultSetHandler = prepareResultHandler(clazz);
        long queryStart = System.currentTimeMillis();
        List<T> records = queryRunner.execute(query, resultSetHandler);
        DbResponse dbResponse = new DbResponse(records);
        dbResponse.setExecutionTime(System.currentTimeMillis() - queryStart);
        return  dbResponse;
    }

    private <T> ResultSetHandler<List<T>> prepareResultHandler(Class<T> clazz) {
        Map<String, String> fieldsMap = getFieldsMap(clazz);
        BeanProcessor beanProcessor = new BeanProcessor(fieldsMap);
        BasicRowProcessor rowProcessor = new BasicRowProcessor(beanProcessor);
        return new BeanListHandler<>(clazz, rowProcessor);
    }

    private Map<String, String> getFieldsMap(Class clazz) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        Map<String, String> fieldsMap = new HashMap<>();
        for(Field field: fields) {
            String value = field.getName();
            if(field.isAnnotationPresent(Column.class)) {
                String key = field.getAnnotation(Column.class).name();
                fieldsMap.put(key, field.getName());
                fieldsMap.put(key.toLowerCase(), value);
                fieldsMap.put(key.toUpperCase(), value);
            }
        }
        return fieldsMap;
    }

}
