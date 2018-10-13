package pl.pg.validator;

import pl.pg.client.rest.Response;
import pl.pg.exception.AssertionException;
import pl.pg.exception.ValidatorNotFoundException;
import pl.pg.json.JsonMapper;
import pl.pg.util.InstanceUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ValidateExecutor {

    private static ValidationSummary validateObject(Object object) {
        pl.pg.annotation.Validator annotation = object.getClass().getAnnotation(pl.pg.annotation.Validator.class);
        if(annotation != null) {
            Validator validator = (Validator) InstanceUtil.createInstanceOf(annotation.value());
            validator.validate(object);
            List summarize = validator.summarize();
            if(!summarize.isEmpty()) {
                ValidationSummary validationSummary = new ValidationSummary();
                validationSummary.setObject(object.toString());
                validationSummary.setFieldValidationSummaries(summarize);
                return validationSummary;
            }
            return null;
        } else {
            throw new ValidatorNotFoundException(object.getClass().toString());
        }
    }

    public static void validate(Object object) {
        if(object instanceof Collection<?>) {
            validateAll((Collection) object);
        }else {
            validateObject(object);
        }
    }

    public static void validate(Response response) {
        validateAll(response.getBodyAsList());
    }

    private static void validateAll(Collection<Object> objects) {
        List<ValidationSummary> summaries = objects.stream()
                .map(o -> validateObject(o))
                .filter(summary -> summary != null)
                .collect(Collectors.toList());
        if(!summaries.isEmpty()) {
            throw new AssertionException(JsonMapper.toJson(summaries));
        }
    }

    public static void compare(Response restResponse, List dbResponse, Class clazz) {
        List employees = JsonMapper.fromJsonToListObject(restResponse.getRawBody().toString(), clazz);
        if(Arrays.deepEquals(employees.toArray(), dbResponse.toArray()) == true) {
           return;
        }
        throw new AssertionException("Rest and database responses are different");
    }
}
