package finalproject.finalproject.service.validation;

import org.apache.commons.validator.routines.EmailValidator;

public class BasicValidation {

    public static void validateField(String field, String fieldName) {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }


    public static void validateEmail(String email) {
        if (email == null || !EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }


    public static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (!password.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit or special character.");
        }
    }
}
