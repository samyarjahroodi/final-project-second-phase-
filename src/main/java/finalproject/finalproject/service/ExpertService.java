package finalproject.finalproject.service;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import org.springframework.stereotype.Service;


@Service
public interface ExpertService extends PersonService<Expert> {
    double averageStarOfExpert(Expert expert);

    void updateRegistrationStatusForSpecificExpert(RegistrationStatus status, Expert expert);

    void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty);
}
