package finalproject.finalproject.repository.impl;

import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.user.Expert;
import repository.ExpertRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ExpertRepositoryImpl
        extends BaseEntityRepositoryImpl<Expert, Integer>
        implements ExpertRepository {


    public ExpertRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Expert> getEntityClass() {
        return Expert.class;
    }

    @Override
    public double averageStarOfExpert(Expert expert) {
        Query query = entityManager.createQuery(
                "SELECT AVG(c.star) " +
                        "FROM Comment c " +
                        "JOIN c.customerOrder o " +
                        "JOIN o.suggestions s " +
                        "WHERE s.expert = :expert AND s.isApproved = :trueParam"
        );
        if (query.getSingleResult().equals(0)) {
            return 0;
        }
        query.setParameter("expert", expert);
        query.setParameter("trueParam", true);
        try {
            return (Double) query.getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }


}
