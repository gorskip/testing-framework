package pl.pg.client.mapper.employee;

import pl.pg.client.entity.Employee;
import pl.pg.client.mapper.EntityMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper extends EntityMapper<Employee> {

    @Override
    public List<Employee> handle(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while(resultSet.next()) {
            Employee employee = new Employee();

            long id = resultSet.getLong("id");
            String name = resultSet.getString("EMPLOYEE_NAME");
            double salary = resultSet.getDouble("salary");

            employee.setId(id);
            employee.setName(name);
            employee.setSalary(salary);
            employees.add(employee);
        }
        return employees;
    }
}
