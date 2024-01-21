package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.service.ExpertService;

import java.util.Optional;

public class ExpertServiceImpl
        extends PersonServiceImpl<Expert, ExpertRepository>
        implements ExpertService {

    public ExpertServiceImpl(ExpertRepository repository) {
        super(repository);
    }

    @Override
    public double averageStarOfExpert(Expert expert) {
        return repository.averageStarOfExpert(expert);
    }

    @Override
    public void changeRegistrationStatus(Integer id) {
        try {
            Optional<Expert> byId = repository.findById(id);
            if (byId.isPresent()) {
                Expert expert = byId.get();
                expert.setRegistrationStatus(RegistrationStatus.ACCEPTED);
                repository.save(expert);
            } else {
                System.err.println("Expert not found : ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

