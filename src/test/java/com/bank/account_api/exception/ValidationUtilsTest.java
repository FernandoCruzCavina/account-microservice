package com.bank.account_api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilsTest {

    @Test
    void validateBalanceShouldThrowForNegativeBalance() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateBalance(-100.0),
                "Expected IllegalArgumentException for negative balance");
    }

    @Test
    void validateAccountTypeShouldThrowForNull() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateAccountType(null),
                "Expected IllegalArgumentException for null accountType");
    }

    @Test
    void validateAccountTypeShouldThrowForEmpty() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateAccountType(""),
                "Expected IllegalArgumentException for empty accountType");
    }

    @Test
    void validateIdShouldThrowForZero() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateId(0),
                "Expected IllegalArgumentException for ID equal to zero");
    }

    @Test
    void validateDateOpenedShouldThrowForZero() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateDateOpened(0),
                "Expected IllegalArgumentException for dateOpened equal to zero");
    }
}
