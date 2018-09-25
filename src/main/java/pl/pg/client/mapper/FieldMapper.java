package pl.pg.client.mapper;

abstract public class FieldMapper<T, E> {

    abstract public T map(E toBeMapped);
}
