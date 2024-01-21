package finalproject.finalproject.service;


import finalproject.finalproject.base.service.BaseEntityService;
import finalproject.finalproject.model.duty.Duty;
import finalproject.finalproject.model.duty.SubDuty;
import org.springframework.stereotype.Service;

import java.util.List;
public interface DutyService extends BaseEntityService<Duty, Integer> {
    List<SubDuty> showSubDutiesOfSpecificDuty(Integer id);

}
