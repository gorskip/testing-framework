package client.entity;

import annotation.Column;
import annotation.MappedBy;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@MappedBy
public class Employee {

    @Column(name = "FIRST_NAME")
    private String name;

    @Column(name = "LAST_NAME")
    private String surname;

    @Column
    private String city;

}
