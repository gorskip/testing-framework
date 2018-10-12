package pl.pg.client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pg.annotation.Column;
import pl.pg.annotation.Validator;
import pl.pg.client.mapper.DateMapper;
import pl.pg.validator.TaskValidator;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@Validator(name = TaskValidator.class)
public class Task {

    private Long id;

    @Column(name = "task_name")
    private String name;

    @Column(mapper = DateMapper.class)
    private LocalDate created;

}
