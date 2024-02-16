package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.exception.StatusException;
import finalproject.finalproject.repository.PersonRepository;
import finalproject.finalproject.service.PersonService;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl<T extends Person, R extends PersonRepository<T>>
        implements PersonService<T> {
    protected final R repository;

    @Autowired
    private final JavaMailSender mailSender;

    @Override
    public List<T> search(SearchForPerson search) {
        return repository.findAll(
                getUserSpecification(search)
        );
    }

    @Override
    public void register(T t, String siteURL) {
        String randomCode = UUID.randomUUID().toString();
        t.setVerificationCode(randomCode);
        t.setEnabled(false);
        repository.save(t);

        sendVerificationEmail(t, siteURL);
    }

    @Override
    public void sendVerificationEmail(T t, String siteURL) {
        String toAddress = t.getEmail();
        String fromAddress = "jahroodi.com";
        String subject = "Please verify your email address";
        String confirmationUrl = siteURL + "/verify?code=" + t.getVerificationCode();
        String message = "To verify your email address, please click the link below:\n" + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(fromAddress);

        mailSender.send(email);
    }

    @Override
    public void verify(String verificationCode) {
        T t = repository.findByVerificationCode(verificationCode);
        if (t == null) {
            throw new NullInputException("user cannot be null");
        } else if (!t.isVerified()) {
            t.setEnabled(true);
            t.setVerified(true);
            repository.save(t);
        } else {
            throw new StatusException("you already verified");
        }
    }

    private Specification<T> getUserSpecification(SearchForPerson search) {
        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction();

            if (search.getFirstname() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("firstname"), search.getFirstname()));
            }

            if (search.getLastname() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("lastname"), search.getLastname()));
            }

            if (search.getRole() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("role"), search.getRole()));
            }
            if (search.getEmail() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("email"), search.getEmail()));
            }

            if (search.getUsername() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("username"), search.getUsername()));
            }

            if (search.getStar() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("star"), search.getStar()));
            }

            if (search.getFieldOfEndeavor() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fieldOfEndeavor"), search.getFieldOfEndeavor()));
            }


            query.orderBy(criteriaBuilder.desc(root.get("star")));

            return predicate;
        };
    }
}
