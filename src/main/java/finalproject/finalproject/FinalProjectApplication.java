package finalproject.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/

@SpringBootApplication
public class FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String rawPassword = "1111";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//
//        System.out.println("Encoded Password: " + encodedPassword);
    }



}
