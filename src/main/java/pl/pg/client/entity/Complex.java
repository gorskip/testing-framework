package pl.pg.client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Complex {

    private Simple simple;
    private String name;
}
