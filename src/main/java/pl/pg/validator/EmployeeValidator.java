package pl.pg.validator;

import pl.pg.client.entity.Employee;

public class EmployeeValidator extends Validator<Employee> {

    @Override
    public void validate(Employee entity) {
        hasMinSize(entity.getName(), 15, "Employee name");

    }
}
