package finalproject.finalproject.service;

import finalproject.finalproject.Entity.duty.SubDuty;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SubDutyService extends BaseService<SubDuty, Integer> {
    void deleteSubDutyFromTheExistDuty(SubDuty subDuty);

    boolean existsByName(String name);

    List<SubDuty> historyOfSubDutyForCustomerOrExpert(String username);
}
