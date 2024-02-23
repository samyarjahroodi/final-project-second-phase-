package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.operation.ConfirmationToken;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.exception.NotFoundException;
import finalproject.finalproject.exception.StatusException;
import finalproject.finalproject.repository.ConfirmationTokenRepository;
import finalproject.finalproject.repository.PersonRepository;
import finalproject.finalproject.service.PersonService;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl<T extends Person, R extends PersonRepository<T>>
        implements PersonService<T> {

    protected final R repository;

    protected final BCryptPasswordEncoder passwordEncoder;

    protected final ConfirmationTokenRepository confirmationTokenRepository;

    protected final EmailServiceImpl emailService;

    @Autowired
    private final JavaMailSender mailSender;

    @Override
    public List<T> search(SearchForPerson search) {
        return repository.findAll(
                getUserSpecification(search)
        );
    }


    @Override
    public T findByUsername(String username) {
        return repository.findByUsernameIfItsExist(username).orElseThrow(
                () -> new NotFoundException(String.format("USER %s NOT FOUND !", username))
        );
    }

    @Override
    public Optional<T> findByUsernameIfExist(String username) {
        return Optional.ofNullable(repository.findByUsername(username));
    }

    @Override
    public double seeWalletCredit(T t) {
        return t.getWallet().getCreditOfWallet();
    }

    @Override
    public T findUserByEmail(String emailAddress) {
        return repository.findByEmail(emailAddress).orElse(null);
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

    public void sendEmail(String emailAddress) {
        T user = repository.findByEmail(emailAddress).orElse(null);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        user.setVerificationCode(confirmationToken.getConfirmationToken());
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("samyarghasemjahroodi@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Hoorah!");
        mailMessage.setText("please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);

    }


    public void verifyEmail(String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            T user = repository.findByEmail(token.getPerson().getEmail()).orElse(null);
            if (user.isVerified()) {
                throw new StatusException("you have already verified");
            }
            if (user instanceof Expert) {
                ((Expert) user).setRegistrationStatus(RegistrationStatus.AWAITING_CONFIRMATION);
                user.setVerified(true);
                user.setEnabled(true);
                repository.save(user);
                return;
            }
            user.setVerified(true);
            user.setEnabled(true);
            repository.save(user);
        } else {
            throw new StatusException("EMAIL DIDNT CONFIRMED");
        }
    }
}
