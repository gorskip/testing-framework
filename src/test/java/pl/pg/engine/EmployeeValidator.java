package pl.pg.engine;

import pl.pg.engine.entity.Employee;
import pl.pg.validator.Validator;

public class EmployeeValidator extends Validator<Employee> {

    @Override
    public void validate(Employee entity) {
        hasMinSize(entity.getName(), 15, "Employee name");

    }
}
