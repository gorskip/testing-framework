package pl.pg.demo.util.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pg.annotation.Column;
import pl.pg.annotation.Validator;
import pl.pg.demo.util.validator.EmployeeValidator;

@Data
@EqualsAndHashCode
@Validator(EmployeeValidator.class)
public class Employee {

    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    private Double salary;
}

