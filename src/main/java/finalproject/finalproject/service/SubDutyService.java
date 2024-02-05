package finalproject.finalproject.service;

import  finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.stereotype.Service;


@Service
public interface SubDutyService extends BaseService<SubDuty,Integer> {
    void deleteSubDutyFromTheExistDuty(SubDuty subDuty);
}
