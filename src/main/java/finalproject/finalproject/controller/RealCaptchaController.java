package finalproject.finalproject.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
public class RealCaptchaController {

    @Autowired
    private DefaultKaptcha captchaProducer;

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        BufferedImage bi = captchaProducer.createImage(capText);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", baos);
        byte[] captchaBytes = baos.toByteArray();
        response.getOutputStream().write(captchaBytes);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
