package pl.pg.engine.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.pg.annotation.Column;
import pl.pg.annotation.Validator;
import pl.pg.engine.EmployeeValidator;

@Data
@EqualsAndHashCode
@ToString
@Validator(EmployeeValidator.class)
//@Mapper(EmployeeMapper.class)
public class Employee {

    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    private Double salary;
}
