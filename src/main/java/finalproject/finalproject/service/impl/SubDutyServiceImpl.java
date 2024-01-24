package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.SubDutyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl
        implements SubDutyService {
    private final SubDutyRepository subDutyRepository;

    public List<SubDuty> showSubDuties() {
       return subDutyRepository.findAll();
    }

    @Override
    public SubDuty saveSubDuty(SubDuty subDuty) {
        return subDutyRepository.save(subDuty);
    }
}