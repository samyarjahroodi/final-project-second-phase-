package finalproject.finalproject.repository.impl;

import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.duty.Duty;
import entity.duty.SubDuty;
import repository.DutyRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DutyRepositoryImpl
        extends BaseEntityRepositoryImpl<Duty, Integer>
        implements DutyRepository {


    public DutyRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Duty> getEntityClass() {
        return Duty.class;
    }

    @Override
    public List<SubDuty> showSubDutiesOfSpecificDuty(Integer id) {
        TypedQuery<SubDuty> query = entityManager.createQuery(
                "SELECT sd FROM SubDuty sd WHERE sd.duty.id = :dutyId",
                SubDuty.class
        );
        query.setParameter("dutyId", id);

        return query.getResultList();
    }

}
