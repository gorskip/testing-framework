package pl.pg.validator;

import java.math.BigDecimal;
import java.util.List;

public interface FieldValidator {

    default boolean hasMaxSize(String value, long max) {
        return value.length() <= max;
    }

    default boolean hasMaxSize(List list, long max) {
        return list.size() <= max;
    }

    default boolean hasMaxSize(Object[] array, long max) {
        return array.length <= max;
    }

    default boolean hasMinSize(String value, long min) {
        return value.length() >= min;
    }

    default boolean hasMinSize(List list, long min) {
        return list.size() >= min;
    }

    default boolean hasMinSize(Object[] array, long min) {
        return array.length >= min;
    }

    default boolean isGreaterThan(long value, long check) {
        return value > check;
    }

    default boolean isGreaterThan(int value, int check) {
        return value > check;
    }

    default boolean isGreaterThan(double value, double check) {
        return value > check;
    }

    default boolean isGreaterThan(BigDecimal value, BigDecimal check) {
        return value.compareTo(check) == 1;
    }

    default boolean isLesserThan(long value, long check) {
        return value < check;
    }

    default boolean isLesserThan(int value, int check) {
        return value < check;
    }

    default boolean isLesserThan(double value, double check) {
        return value < check;
    }

    default boolean isLesserThan(BigDecimal value, BigDecimal check) {
        return value.compareTo(check) == -1;
    }

    default boolean isPositive(long value) {
        return value > 0;
    }

    default boolean isPositive(int value) {
        return value > 0;
    }

    default boolean isPositive(double  value) {
        return value > 0;
    }

    default boolean isPositive(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 1;
    }

    default boolean isNegative(long value) {
        return value < 0;
    }

    default boolean isNegative(int value) {
        return value < 0;
    }

    default boolean isNegative(double  value) {
        return value < 0;
    }

    default boolean isNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 1;
    }

    default boolean isNonNegative(long value) {
        return value >= 0;
    }

    default boolean isNonNegative(int value) {
        return value >= 0;
    }

    default boolean isNonNegative(double value) {
        return value >= 0;
    }

    default boolean isNonNegative(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) >= 0;
    }

    default boolean isAlphanumeric(String value) {
        return matches(value, "[a-zA-Z0-9]+");
    }

    default boolean matches(String value, String regex) {
        return value.matches(regex);
    }
}
