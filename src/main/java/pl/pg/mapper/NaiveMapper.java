package pl.pg.mapper;

public class NaiveMapper implements SourceMapper {

    @Override
    public <T> T map(Object restResponse, Object dbResponse, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return null;
    }
}
