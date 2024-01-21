package finalproject.finalproject.service;


import finalproject.finalproject.base.service.BaseEntityService;
import finalproject.finalproject.model.user.Expert;
import org.springframework.stereotype.Service;

@Service
public interface ExpertService extends PersonService<Expert> {
    double averageStarOfExpert(Expert expert);

     void changeRegistrationStatus(Integer id);
}
