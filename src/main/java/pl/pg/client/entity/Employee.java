package pl.pg.client.entity;

import pl.pg.annotation.Column;
import pl.pg.annotation.MappedBy;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@MappedBy
public class Employee {

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private Double salary;

}
