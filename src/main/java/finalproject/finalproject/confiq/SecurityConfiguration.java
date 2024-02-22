package finalproject.finalproject.confiq;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.exception.NotFoundException;
import finalproject.finalproject.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final PersonService<Person> personService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/customer/create-Customer").permitAll()
                        .requestMatchers("/expert/createExpert").permitAll()
                        .requestMatchers("admin/**").hasRole("ADMIN")
                        .requestMatchers("customer/**").hasAnyRole("CUSTOMER")
                        .requestMatchers("expert/**").hasRole("EXPERT")
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((username) -> personService
                        .findByUsernameIfExist(username)
                        .orElseThrow(() -> new NotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
    }

}