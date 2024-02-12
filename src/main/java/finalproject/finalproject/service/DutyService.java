package finalproject.finalproject.service;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DutyService extends BaseService<Duty,Integer> {
    List<SubDuty> showSubDutiesOfSpecificDuty(Duty duty);
    boolean existsByName(String name);
}
