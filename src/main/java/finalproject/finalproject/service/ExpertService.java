package finalproject.finalproject.service;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import org.springframework.stereotype.Service;


@Service
public interface ExpertService extends PersonService<Expert> {
/*    double averageStarOfExpert(Expert expert);*/

    void updateRegistrationStatusForSpecificExpert(Expert expert);

    void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty);
}
