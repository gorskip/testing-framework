package mapper;

import annotation.Column;
import lombok.Data;

import java.lang.reflect.Field;

@Data
public class SourceField {

    private final Field field;
    private final Column column;
}
