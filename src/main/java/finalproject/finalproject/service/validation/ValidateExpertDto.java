package finalproject.finalproject.service.validation;

import finalproject.finalproject.exception.NotValidFormatFileException;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;


public class ValidateExpertDto extends BasicValidation {


    public static void validateExpertDtoRequest(ExpertDtoRequest expertDtoRequest) {
        if (expertDtoRequest == null) {
            throw new IllegalArgumentException("User DTO cannot be null");
        }
        validateField(expertDtoRequest.getFirstname(), "First name");
        validateField(expertDtoRequest.getLastname(), "Last name");
        validateEmail(expertDtoRequest.getEmail());
        validateField(expertDtoRequest.getUsername(), "Username");
        validatePassword(expertDtoRequest.getPassword());
        validatePathName(expertDtoRequest.getPathName(), "Path name");
        validateFieldOfEndeavor(expertDtoRequest.getFieldOfEndeavor(),"Field Of Endeavor");
    }


    private static void validatePathName(String pathName, String fieldName) {
        if (pathName == null || pathName.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (!pathName.toLowerCase().endsWith(".jpg")) {
            throw new NotValidFormatFileException("Only JPG images are allowed");
        }
    }

    private static void validateFieldOfEndeavor(String fieldOfEndeavor, String fieldName) {
        if (fieldOfEndeavor == null || fieldOfEndeavor.isBlank()) {
            throw new IllegalArgumentException("Field Of Endeavor cannot be null or empty");
        }
    }
}
