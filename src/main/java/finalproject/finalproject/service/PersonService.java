package finalproject.finalproject.service;

import finalproject.finalproject.base.service.BaseEntityService;
import finalproject.finalproject.model.user.Person;

public interface PersonService<T extends Person>
        extends BaseEntityService<T, Integer> {
}