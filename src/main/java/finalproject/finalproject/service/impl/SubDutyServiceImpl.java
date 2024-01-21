package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.duty.SubDuty;
import repository.SubDutyRepository;
import service.SubDutyService;


public class SubDutyServiceImpl
        extends BaseEntityServiceImpl<SubDuty, Integer, SubDutyRepository>
        implements SubDutyService {

    public SubDutyServiceImpl(SubDutyRepository repository) {
        super(repository);
    }

}