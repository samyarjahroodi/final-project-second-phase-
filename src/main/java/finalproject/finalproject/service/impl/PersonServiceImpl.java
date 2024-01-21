package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.repository.PersonRepository;
import finalproject.finalproject.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PersonServiceImpl<T extends Person, R extends PersonRepository<T>>
        implements PersonService<T> {
    protected final R repository;


}
