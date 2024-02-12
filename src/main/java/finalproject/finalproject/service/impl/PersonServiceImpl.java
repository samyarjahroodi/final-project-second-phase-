package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.repository.PersonRepository;
import finalproject.finalproject.service.PersonService;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl<T extends Person, R extends PersonRepository<T>>
        implements PersonService<T> {
    protected final R repository;


    @Override
    public List<T> search(SearchForPerson search) {
        return repository.findAll(
                getUserSpecification(search)
        );
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
