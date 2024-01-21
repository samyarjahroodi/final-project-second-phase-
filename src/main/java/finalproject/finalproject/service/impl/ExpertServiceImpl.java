package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.user.Expert;
import entity.user.RegistrationStatus;
import repository.ExpertRepository;
import service.ExpertService;

import java.util.Optional;

public class ExpertServiceImpl
        extends BaseEntityServiceImpl<Expert, Integer, ExpertRepository>
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
                repository.saveOrUpdate(expert);
            } else {
                System.err.println("Expert not found : ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

