package verify.diff;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MoveDiff {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String op;
    private String from;
    private String path;
}
