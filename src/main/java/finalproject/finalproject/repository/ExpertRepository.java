package finalproject.finalproject.repository;

import finalproject.finalproject.model.user.Expert;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends PersonRepository<Expert>{
    double averageStarOfExpert(Expert expert);
}
