package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.SubDutyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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