package pl.pg.client.mapper;

abstract public class FieldMapper<E, T> {

    abstract public T map(E toBeMapped);
}
