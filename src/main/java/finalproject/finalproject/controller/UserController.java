package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class UserController {

    private PersonService<Person> personService;

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmPerson(@RequestParam("token") String confirmationToken) {
        personService.verifyEmail(confirmationToken);
        return new ResponseEntity<>("EMAIL VERIFIED SUCCESSFULLY", HttpStatus.OK);
    }
}