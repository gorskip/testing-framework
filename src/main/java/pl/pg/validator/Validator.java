package pl.pg.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

abstract public class Validator<T> implements FieldValidator {

    public static final String CUSTOM = "CUSTOM";
    private static final String MAX_SIZE = "MAX_SIZE";
    private static final String MIN_SIZE = "MIN_SIZE";
    private static final String GREATER_THAN = "GREATER_THAN";
    private static final String LESSER_THAN = "LESSER_THAN";
    private static final String POSITIVE = "POSITIVE";
    private static final String NEGATIVE = "NEGATIVE";
    private static final String ALPHANUMERIC = "ALPHANUMERIC";
    private static final String MATCHES = "MATCHES";

    private final List<FieldValidationSummary> fieldsSummary = new ArrayList<>();

    public abstract void validate(T entity);

    protected List<FieldValidationSummary> summarize() {
        return fieldsSummary;
    }

    protected void add(boolean test, FieldValidationSummary fieldValidationSummary) {
        if(!test) {
            fieldsSummary.add(fieldValidationSummary);
        }
    }

    protected void add(boolean test, String fieldName, String validationMessage) {
        FieldValidationSummary summary = new FieldValidationSummary();
        summary.setFieldName(fieldName);
        summary.setValidationType(CUSTOM);
        summary.setMessage(validationMessage);
        add(test, summary);
    }

    protected void hasMaxSize(String value, long max, String fieldName) {
        if(!hasMaxSize(value, max)) {
            addFieldValidationSummary(fieldName, MAX_SIZE, value.length(), max);
        }
    }

    private void addFieldValidationSummary(String fieldName, String type, Object value, Object check) {
        FieldValidationSummary summary = new FieldValidationSummary();
        summary.setFieldName(fieldName);
        summary.setValidationType(type);
        summary.setActual(value);
        summary.setExpected(check);
        fieldsSummary.add(summary);
    }

    private void addFieldValidationSummary(String fieldName, String type, Object value) {
        FieldValidationSummary summary = new FieldValidationSummary();
        summary.setValidationType(type);
        summary.setFieldName(fieldName);
        summary.setActual(value);
        fieldsSummary.add(summary);
    }

    protected void hasMaxSize(List list, long max, String fieldName) {
        if(!hasMaxSize(list, max)) {
            addFieldValidationSummary(fieldName, MAX_SIZE, list.size(), max);
        }
    }

    protected void hasMaxSize(Object[] array, long max, String fieldName) {
        if(!hasMaxSize(array, max)){
            addFieldValidationSummary(fieldName, MAX_SIZE, array.length, max);
        }
    }

    protected void hasMinSize(String value, long min, String fieldName) {
       if(!hasMinSize(value, min)) {
           addFieldValidationSummary(fieldName, MIN_SIZE, value.length(), min);
       }
    }

    protected void hasMinSize(List list, long min, String fieldName) {
        if(!hasMinSize(list, min)) {
            addFieldValidationSummary(fieldName, MIN_SIZE, list.size(), min);
        }
    }

    protected void hasMinSize(Object[] array, long min, String fieldName) {
        if(!hasMinSize(array, min)) {
            addFieldValidationSummary(fieldName, MIN_SIZE, array.length, min);
        }
    }

    protected void isGreaterThan(long value, long check, String fieldName) {
        if(!isGreaterThan(value, check)) {
            addFieldValidationSummary(fieldName, GREATER_THAN, value, check);
        }
    }

    protected void isGreaterThan(int value, int check, String fieldName) {
        if(!isGreaterThan(value, check)) {
            addFieldValidationSummary(fieldName, GREATER_THAN, value, check);
        }
    }

    protected void isGreaterThan(double value, double check, String fieldName) {
        if(!isGreaterThan(value, check)) {
            addFieldValidationSummary(fieldName, GREATER_THAN, value, check);
        }
    }

    protected void isGreaterThan(BigDecimal value, BigDecimal check, String fieldName) {
        if(!isGreaterThan(value, check)) {
            addFieldValidationSummary(fieldName, GREATER_THAN, value.toString(), check);
        }
    }

    protected void isLesserThan(long value, long check, String fieldName) {
        if(!isLesserThan(value, check)) {
            addFieldValidationSummary(fieldName, LESSER_THAN, value, check);
        }
    }

    protected void isLesserThan(int value, int check, String fieldName) {
        if(!isLesserThan(value, check)) {
            addFieldValidationSummary(fieldName, LESSER_THAN, value, check);
        }
    }

    protected void isLesserThan(double value, double check, String fieldName) {
        if(!isLesserThan(value, check)) {
            addFieldValidationSummary(fieldName, LESSER_THAN, value, check);
        }
    }

    protected void isLesserThan(BigDecimal value, BigDecimal check, String fieldName) {
        if(!isLesserThan(value, check)) {
            addFieldValidationSummary(fieldName, LESSER_THAN, value.toString(), check);
        }
    }

    protected void isPositive(long value, String fieldName) {
        if(!isPositive(value)) {
            addFieldValidationSummary(fieldName, POSITIVE, value);
        }
    }

    protected void isPositive(int value, String fieldName) {
        if(!isPositive(value)) {
            addFieldValidationSummary(fieldName, POSITIVE, value);
        }
    }

    protected void isPositive(double  value, String fieldName) {
        if(!isPositive(value)) {
            addFieldValidationSummary(fieldName, POSITIVE, value);
        }
    }

    protected void isPositive(BigDecimal value, String fieldName) {
        if(!isPositive(value)) {
            addFieldValidationSummary(fieldName, POSITIVE, value.toString());
        }
    }

    protected void isNegative(long value, String fieldName) {
        if(!isNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isNegative(int value, String fieldName) {
        if(!isNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isNegative(double  value, String fieldName) {
        if(!isNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isNegative(BigDecimal value, String fieldName) {
        if(!isNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value.toString());
        }
    }

    protected void isNonNegative(long value, String fieldName) {
        if(!isNonNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isNonNegative(int value, String fieldName) {
        if(!isNonNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isNonNegative(double value, String fieldName) {
        if(!isNonNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isNonNegative(BigDecimal value, String fieldName) {
        if(!isNonNegative(value)) {
            addFieldValidationSummary(fieldName, NEGATIVE, value);
        }
    }

    protected void isAlphanumeric(String value, String fieldName) {
       if(!isAlphanumeric(value)) {
           addFieldValidationSummary(fieldName, ALPHANUMERIC, value);
       }
    }

    protected void matches(String value, String regex, String fieldName) {
        if(!matches(value, regex)) {
            addFieldValidationSummary(fieldName, MATCHES, value, regex);
        }
    }
}
