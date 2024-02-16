package finalproject.finalproject.service;

import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService<T extends Person> {
    List<T> search(SearchForPerson search);

    void register(T t,String siteURL );

    void sendVerificationEmail(T t, String siteURL);

    void verify(String verificationCode);


}