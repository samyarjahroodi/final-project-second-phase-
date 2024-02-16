package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.service.dto.request.CustomerOrderDtoRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import finalproject.finalproject.service.impl.CustomerServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AppController {

    private final CustomerServiceImpl customerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDtoRequest userDtoRequest, HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        try {
            customerService.createCustomer(userDtoRequest, siteURL);
            return new ResponseEntity<>("Please check your email for verification", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Failed to send verification email", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String code) {
        customerService.verify(code);
        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
    }
}
