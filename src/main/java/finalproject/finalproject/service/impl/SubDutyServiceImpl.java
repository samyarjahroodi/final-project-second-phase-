package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.SubDutyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubDutyServiceImpl
        implements SubDutyService {
    private final SubDutyRepository subDutyRepository;


    @Override
    public void deleteSubDutyFromTheExistDuty(SubDuty subDuty) {
        subDutyRepository.deleteSubDutyFromTheExistDuty(subDuty);
    }
}