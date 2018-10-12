package pl.pg.client.mapper;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract public class EntityMapper<T> implements ResultSetHandler<List<T>> {

    abstract public List<T> handle(ResultSet resultSet) throws SQLException;

}
