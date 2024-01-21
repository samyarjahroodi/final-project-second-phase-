package finalproject.finalproject.repository.impl;


import base.reposiotry.Impl.BaseEntityRepositoryImpl;
import entity.user.Person;
import repository.PersonRepository;

import javax.persistence.EntityManager;

public abstract class PersonRepositoryImpl<T extends Person>
        extends BaseEntityRepositoryImpl<T, Integer>
        implements PersonRepository<T> {
    public PersonRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
