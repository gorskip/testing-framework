package pl.pg.engine;

import org.apache.commons.lang3.StringUtils;
import pl.pg.engine.entity.Task;
import pl.pg.validator.Validator;

public class TaskValidator extends Validator<Task> {

    @Override
    public void validate(Task entity) {
        hasMinSize(entity.getName(), 0, "name");
        isAlphanumeric(StringUtils.deleteWhitespace(entity.getName()), "name");
    }
}
