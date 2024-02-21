//package finalproject.finalproject.controller;
//
//import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
//import finalproject.finalproject.service.dto.request.UserDtoRequest;
//import finalproject.finalproject.service.impl.CustomerServiceImpl;
//import finalproject.finalproject.service.impl.ExpertServiceImpl;
//import jakarta.mail.MessagingException;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//
//@RestController
//@RequiredArgsConstructor
//public class AppController {
//
//    private final CustomerServiceImpl customerService;
//    private final ExpertServiceImpl expertService;
//
//    @PostMapping("/register-customer")
//    public ResponseEntity<String> registerCustomer(@RequestBody UserDtoRequest userDtoRequest, HttpServletRequest request) {
//        String siteURL = request.getRequestURL().toString().replace(request.getRequestURI(), "");
//        try {
//            customerService.createCustomer(userDtoRequest, siteURL);
//            return new ResponseEntity<>("Please check your email for verification", HttpStatus.OK);
//        } catch (MessagingException e) {
//            return new ResponseEntity<>("Failed to send verification email", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/verify-expert")
//    public ResponseEntity<String> verifyExpert(@RequestParam String code) {
//        expertService.verify(code);
//        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
//    }
//}
