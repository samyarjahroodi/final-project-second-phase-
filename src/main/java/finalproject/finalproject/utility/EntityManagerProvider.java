package finalproject.finalproject.utility;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
    @Getter
    private final static EntityManager entityManager;

    static {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        entityManager = emf.createEntityManager();
    }
}
