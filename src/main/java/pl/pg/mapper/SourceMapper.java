package pl.pg.mapper;

public interface SourceMapper {

    <T> T map(Object restResponse, Object dbResponse,  Class<T> clazz) throws IllegalAccessException, InstantiationException;
}
