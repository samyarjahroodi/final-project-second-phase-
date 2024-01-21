package finalproject.finalproject.repository.impl;

import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.duty.SubDuty;
import repository.SubDutyRepository;

import javax.persistence.EntityManager;

public class SubDutyRepositoryImpl
        extends BaseEntityRepositoryImpl<SubDuty, Integer>
        implements SubDutyRepository {

    public SubDutyRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<SubDuty> getEntityClass() {
        return SubDuty.class;
    }

}
