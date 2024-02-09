package finalproject.finalproject.service;

import jakarta.servlet.http.HttpSession;


public interface CaptchaService {
    boolean validateCaptcha(String captchaValue, HttpSession session);

    String generateCaptcha(HttpSession session);
}
