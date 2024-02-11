package finalproject.finalproject.service.impl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import finalproject.finalproject.service.RealCaptchaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealCaptchaServiceImpl implements RealCaptchaService {
    @Autowired
    private final HttpSession httpSession;

    @Autowired
    private DefaultKaptcha captchaProducer;

    public RealCaptchaServiceImpl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public String generateCaptcha() {
        return captchaProducer.createText();
    }

    @Override
    public boolean validateCaptcha(String userInput) {
        String expectedCaptchaText = (String) httpSession.getAttribute("captchaText");
        return userInput.equalsIgnoreCase(expectedCaptchaText);
    }

}
