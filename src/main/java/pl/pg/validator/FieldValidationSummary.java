package pl.pg.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FieldValidationSummary {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fieldName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String validationType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object actual;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object expected;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

}
