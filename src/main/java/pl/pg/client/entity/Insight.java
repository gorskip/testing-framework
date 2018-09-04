package client.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class Insight {

    private String author;
    private String message;
    private List<Tag> tags;
}
