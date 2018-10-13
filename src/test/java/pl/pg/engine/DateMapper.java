package pl.pg.engine;

import pl.pg.client.mapper.FieldMapper;

import java.sql.Date;
import java.time.LocalDate;

public class DateMapper extends FieldMapper<Date, LocalDate> {

    @Override
    public LocalDate map(Date toBeMapped) {
        return toBeMapped.toLocalDate();
    }
}
