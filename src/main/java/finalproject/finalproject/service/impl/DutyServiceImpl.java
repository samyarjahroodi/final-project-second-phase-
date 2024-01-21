package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.duty.Duty;
import entity.duty.SubDuty;
import repository.DutyRepository;
import service.DutyService;

import java.util.List;

public class DutyServiceImpl
        extends BaseEntityServiceImpl<Duty, Integer, DutyRepository>
        implements DutyService {

    public DutyServiceImpl(DutyRepository repository) {
        super(repository);
    }


    @Override
    public List<SubDuty> showSubDutiesOfSpecificDuty(Integer id) {
        return repository.showSubDutiesOfSpecificDuty(id);
    }
}