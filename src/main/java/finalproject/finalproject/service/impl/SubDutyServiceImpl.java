package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.Role;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.SubDutyService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class SubDutyServiceImpl
        implements SubDutyService {
    private final SubDutyRepository subDutyRepository;

    private final SearchPersonServiceImpl personService;


    @Override
    public void deleteSubDutyFromTheExistDuty(SubDuty subDuty) {
        if (subDuty == null) {
            throw new NullInputException("sub duty cannot be null");
        }
        subDutyRepository.deleteSubDutyFromTheExistDuty(subDuty);
    }

    @Override
    public boolean existsByName(String name) {
        return subDutyRepository.existsByName(name);
    }

    @Override
    public List<SubDuty> historyOfSubDutyForCustomerOrExpert(String username) {
        Specification<SubDuty> subDutyByUser = findSubDutyByUser(username);
        return subDutyRepository.findAll(subDutyByUser);
    }

    private Specification<SubDuty> findSubDutyByUser(String username) {
        return (root, query, criteriaBuilder) -> {
            Role userRole = personService.findByUsername(username).getRole();

            if (userRole.equals(Role.ROLE_EXPERT)) {
                return criteriaBuilder.equal(root.join("experts", JoinType.INNER).get("username"), username);
            } else if (userRole.equals(Role.ROLE_CUSTOMER)) {
                return criteriaBuilder.equal(
                        root.join("customerOrder", JoinType.INNER).join("customer", JoinType.INNER).get("username"),
                        username
                );
            } else {
                return null;
            }
        };
    }


    @Override
    public <S extends SubDuty> S save(S entity) {
        return subDutyRepository.save(entity);
    }

    @Override
    public Optional<SubDuty> findById(Integer integer) {
        return subDutyRepository.findById(integer);
    }

    @Override
    public List<SubDuty> findAll() {
        return subDutyRepository.findAll();
    }

    @Override
    public List<SubDuty> findAllById(Iterable<Integer> integers) {
        return subDutyRepository.findAllById(integers);
    }

    @Override
    public List<SubDuty> findAll(Sort sort) {
        return subDutyRepository.findAll(sort);
    }

    @Override
    public Page<SubDuty> findAll(Pageable pageable) {
        return subDutyRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        subDutyRepository.deleteById(integer);
    }

    @Override
    public void delete(SubDuty entity) {
        subDutyRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends SubDuty> entities) {
        subDutyRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        subDutyRepository.deleteAll();
    }

    @Override
    public SubDuty getReferenceById(Integer integer) {
        return subDutyRepository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }
}