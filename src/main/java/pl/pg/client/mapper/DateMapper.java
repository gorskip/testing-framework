package pl.pg.client.mapper;

import java.sql.Date;
import java.time.LocalDate;

public class DateMapper extends FieldMapper<Date, LocalDate> {

    @Override
    public LocalDate map(Date toBeMapped) {
        return toBeMapped.toLocalDate();
    }
}
