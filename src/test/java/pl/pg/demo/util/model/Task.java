package pl.pg.demo.util.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pg.annotation.Column;
import pl.pg.demo.util.mapper.DateMapper;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class Task {

    private Long id;
    @Column(name = "task_name")
    private String name;
    @Column(mapper = DateMapper.class)
    private LocalDate created;
}
