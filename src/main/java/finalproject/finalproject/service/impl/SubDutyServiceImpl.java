package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.SubDutyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class SubDutyServiceImpl
        implements SubDutyService {
    private final SubDutyRepository subDutyRepository;


    @Override
    public void deleteSubDutyFromTheExistDuty(SubDuty subDuty) {
        if (subDuty == null) {
            throw new IllegalArgumentException("sub duty cannot be null");
        }
        subDutyRepository.deleteSubDutyFromTheExistDuty(subDuty);
    }

}