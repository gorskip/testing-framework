package pl.pg.client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.pg.annotation.Column;
import pl.pg.annotation.MappedBy;

@Data
@EqualsAndHashCode
@MappedBy
@ToString
public class Employee {

    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String name;

    private Double salary;

}
