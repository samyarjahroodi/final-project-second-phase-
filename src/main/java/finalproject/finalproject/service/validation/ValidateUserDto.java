package finalproject.finalproject.service.validation;

import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import org.apache.commons.validator.routines.EmailValidator;


public class ValidateUserDto {
    public static void validateUserDtoRequest(UserDtoRequest userDtoRequest) {
        if (userDtoRequest == null) {
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        validateField(userDtoRequest.getFirstname(), "First name");
        validateField(userDtoRequest.getLastname(), "Last name");
        validateEmail(userDtoRequest.getEmail());
        validateField(userDtoRequest.getUsername(), "Username");
        validatePassword(userDtoRequest.getPassword());
    }


    public static void validateExpertDtoRequest(ExpertDtoRequest expertDtoRequest) {
        if (expertDtoRequest == null) {
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        validateField(expertDtoRequest.getFirstname(), "First name");
        validateField(expertDtoRequest.getLastname(), "Last name");
        validateEmail(expertDtoRequest.getEmail());
        validateField(expertDtoRequest.getUsername(), "Username");
        validatePassword(expertDtoRequest.getPassword());
    }


    private static void validateField(String field, String fieldName) {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }


    private static void validateEmail(String email) {
        if (email == null || !EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }


    private static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (!password.matches("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit or special character.");
        }
    }

}
