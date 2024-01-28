package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.service.DutyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DutyServiceImpl
        implements DutyService {

    private final DutyRepository dutyRepository;

    @Override
    public List<SubDuty> showSubDutiesOfSpecificDuty(Duty duty) {
        if (duty == null) {
            throw new IllegalArgumentException("Duty must not be null");
        }
        return duty.getSubDuties();
    }
}