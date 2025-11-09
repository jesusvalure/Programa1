package com.mycompany.programa1matriculacalificaciones.util;

/**
 * Small reusable validation utilities for the application.
 * Keep methods simple and deterministic so they are easy to test.
 */
public final class Validator {

    private Validator() {}

    /**
     * Basic email validation using a permissive regex.
     */
    public static boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    }

    /**
     * Phone validation: allows digits, spaces, plus and hyphen. 6-20 chars.
     */
    public static boolean isPhoneValid(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        return phone.matches("^[+]?[- 0-9]{6,20}$");
    }

    /**
     * ID validation: digits only, between 4 and 20 digits. Adjustable rule.
     */
    public static boolean isIdValid(String id) {
        if (id == null || id.isEmpty()) return false;
        return id.matches("^\\d{4,20}$");
    }

    /**
     * Date validation (basic): supports yyyy-MM-dd or dd/MM/yyyy formats.
     */
    public static boolean isDateValid(String date) {
        if (date == null || date.isEmpty()) return false;
        return date.matches("^\\d{4}-\\d{2}-\\d{2}$") || date.matches("^\\d{2}/\\d{2}/\\d{4}$");
    }
}
