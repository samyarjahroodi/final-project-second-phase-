package finalproject.finalproject.service.impl;

import base.service.Impl.BaseEntityServiceImpl;
import entity.user.Person;
import repository.PersonRepository;
import service.PersonService;

public class PersonServiceImpl<T extends Person, R extends PersonRepository<T>>
        extends BaseEntityServiceImpl<T, Integer, R>
        implements PersonService<T> {

    public PersonServiceImpl(R repository) {
        super(repository);
    }
}
