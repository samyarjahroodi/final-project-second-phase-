package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.service.DutyService;
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
@Transactional(readOnly = true)
public class DutyServiceImpl
        implements DutyService {

    private final DutyRepository dutyRepository;

    @Override
    public List<SubDuty> showSubDutiesOfSpecificDuty(Duty duty) {
        if (duty == null) {
            throw new NullInputException("Duty must not be null");
        }
        return duty.getSubDuties();
    }

    @Override
    public boolean existsByName(String name) {
        return dutyRepository.existsByName(name);
    }

    @Override
    public <S extends Duty> S save(S entity) {
        return dutyRepository.save(entity);
    }

    @Override
    public Optional<Duty> findById(Integer integer) {
        return dutyRepository.findById(integer);
    }

    @Override
    public List<Duty> findAll() {
        return dutyRepository.findAll();
    }

    @Override
    public List<Duty> findAllById(Iterable<Integer> integers) {
        return dutyRepository.findAllById(integers);
    }

    @Override
    public List<Duty> findAll(Sort sort) {
        return dutyRepository.findAll(sort);
    }

    @Override
    public Page<Duty> findAll(Pageable pageable) {
        return dutyRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        dutyRepository.deleteById(integer);
    }

    @Override
    public void delete(Duty entity) {
        dutyRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Duty> entities) {
        dutyRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        dutyRepository.deleteAll();
    }

    @Override
    public Duty getReferenceById(Integer integer) {
        return dutyRepository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }
}