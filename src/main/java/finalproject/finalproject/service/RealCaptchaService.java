package finalproject.finalproject.service;


public interface RealCaptchaService {
    String generateCaptcha();

    boolean validateCaptcha(String userInput);
}
