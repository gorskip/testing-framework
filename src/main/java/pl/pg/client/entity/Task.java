package pl.pg.client.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.pg.annotation.Column;
import pl.pg.client.mapper.DateMapper;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class Task {

    private Long id;
    @Column(name = "task_name")
    private String name;
    @Column(mapper = DateMapper.class)
    @JsonSerialize
    private LocalDate created;

}
