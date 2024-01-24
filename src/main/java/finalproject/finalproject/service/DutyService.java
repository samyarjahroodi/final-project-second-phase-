package finalproject.finalproject.service;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;

import java.util.List;
public interface DutyService {
    List<SubDuty> showSubDutiesOfSpecificDuty(Duty duty);

}
