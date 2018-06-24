package verify.diff;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Diff {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String op;
    private String path;
    private Object value;
}
