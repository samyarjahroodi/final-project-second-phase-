package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.repository.ConfirmationTokenRepository;
import finalproject.finalproject.repository.PersonRepository;
import finalproject.finalproject.service.SearchPersonService;

import finalproject.finalproject.service.dto.request.SearchForPerson;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class SearchPersonServiceImpl
        extends PersonServiceImpl<Person, PersonRepository<Person>>
        implements SearchPersonService {


    public SearchPersonServiceImpl(PersonRepository<Person> repository, BCryptPasswordEncoder passwordEncoder, ConfirmationTokenRepository confirmationTokenRepository, EmailServiceImpl emailService, JavaMailSender mailSender) {
        super(repository, passwordEncoder, confirmationTokenRepository, emailService, mailSender);
    }

    @Override
    public List<Person> searchForUsers(SearchForPerson search) {
        return search(search);
    }


}