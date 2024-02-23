package finalproject.finalproject.service;

import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PersonService<T extends Person> {
    List<T> search(SearchForPerson search);

/*    void register(T t, String siteURL);

    void sendVerificationEmail(T t, String siteURL);*/

  /*  void verify(String verificationCode);*/

    T findByUsername(String username);

    Optional<T> findByUsernameIfExist(String username);

    double seeWalletCredit(T t);

    T findUserByEmail(String emailAddress);

    void verifyEmail(String confirmationToken);

}