package finalproject.finalproject.service.validation;

import finalproject.finalproject.service.dto.request.CardDtoRequest;

public class ValidateCardDto {

    public static void validateCardDto(CardDtoRequest cardDto) throws IllegalArgumentException {
        if (!isValidCardNumber(cardDto.getCardNumber())) {
            throw new IllegalArgumentException("Invalid card number");
        }

        if (!isValidMonth(cardDto.getMonth())) {
            throw new IllegalArgumentException("Invalid month");
        }

        if (!isValidYear(cardDto.getYear())) {
            throw new IllegalArgumentException("Invalid year");
        }

        if (!isValidCvv2(cardDto.getCvv2())) {
            throw new IllegalArgumentException("Invalid CVV2");
        }

        if (!isValidPassword(cardDto.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    private static boolean isValidCardNumber(String cardNumber) {
        return cardNumber != null && cardNumber.matches("[0-9]+") && cardNumber.length() == 16;
    }
    private static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    private static boolean isValidYear(int year) {
        return year >= 0 && year <= 99;
    }

    private static boolean isValidCvv2(int cvv2) {
        String cvv2Str = String.valueOf(cvv2);
        return cvv2Str.length() >= 3 && cvv2Str.length() <= 4 && cvv2Str.matches("[0-9]+");
    }

    private static boolean isValidPassword(int password) {
        String passwordStr = String.valueOf(password);
        return passwordStr.length() == 4 && passwordStr.matches("[0-9]+");
    }
}
