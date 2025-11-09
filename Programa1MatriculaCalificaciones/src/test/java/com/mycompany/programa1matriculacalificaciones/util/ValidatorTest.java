package com.mycompany.programa1matriculacalificaciones.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

    @Test
    public void emailValidation_validAndInvalid() {
        assertTrue(Validator.isEmailValid("user@example.com"));
        assertTrue(Validator.isEmailValid("first.last@sub.domain.co"));
        assertFalse(Validator.isEmailValid("no-at-sign"));
        assertFalse(Validator.isEmailValid("@missing-local.com"));
        assertFalse(Validator.isEmailValid(null));
    }

    @Test
    public void phoneValidation_validAndInvalid() {
        assertTrue(Validator.isPhoneValid("+506 8888 7777"));
        assertTrue(Validator.isPhoneValid("88887777"));
        assertTrue(Validator.isPhoneValid("+1-800-123456"));
        assertFalse(Validator.isPhoneValid("abc-123"));
        assertFalse(Validator.isPhoneValid(""));
        assertFalse(Validator.isPhoneValid(null));
    }

    @Test
    public void idValidation_validAndInvalid() {
        assertTrue(Validator.isIdValid("1234"));
        assertTrue(Validator.isIdValid("000012345678901234"));
        assertFalse(Validator.isIdValid("abc123"));
        assertFalse(Validator.isIdValid("123")); // too short
        assertFalse(Validator.isIdValid(null));
    }

    @Test
    public void dateValidation_formats() {
        assertTrue(Validator.isDateValid("2025-11-08"));
        assertTrue(Validator.isDateValid("08/11/2025"));
        assertFalse(Validator.isDateValid("11-08-2025"));
        assertFalse(Validator.isDateValid("invalid"));
        assertFalse(Validator.isDateValid(null));
    }
}
