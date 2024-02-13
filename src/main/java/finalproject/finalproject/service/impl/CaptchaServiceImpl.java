package finalproject.finalproject.service.impl;

import finalproject.finalproject.service.CaptchaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final int CAPTCHA_LENGTH = 4;

    public String generateCaptcha(HttpSession session) {
        String captcha = generateString();
        session.setAttribute("captcha", captcha);
        return captcha;
    }

    private String generateString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567890";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        while (captcha.length() < CaptchaServiceImpl.CAPTCHA_LENGTH) {
            int index = (int) (random.nextFloat() * characters.length());
            captcha.append(characters.charAt(index));
        }
        return captcha.toString();
    }

    public boolean validateCaptcha(String captchaValue, HttpSession session) {
        String sessionCaptcha = (String) session.getAttribute("captcha");
        return captchaValue.equals(sessionCaptcha);
    }
}