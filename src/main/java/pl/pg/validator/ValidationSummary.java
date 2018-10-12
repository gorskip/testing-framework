package pl.pg.validator;

import lombok.Data;

import java.util.List;

@Data
public class ValidationSummary {

    private Object object;
    private List<FieldValidationSummary> fieldValidationSummaries;

}
