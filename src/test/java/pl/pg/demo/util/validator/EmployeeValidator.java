package pl.pg.demo.util.validator;

import pl.pg.demo.util.model.Employee;
import pl.pg.validator.Validator;

public class EmployeeValidator extends Validator<Employee> {
    @Override
    public void validate(Employee employee) {

        hasMinSize(employee.getName(), 3, "Employee name");
        isPositive(employee.getSalary(), "Salary");
    }
}
