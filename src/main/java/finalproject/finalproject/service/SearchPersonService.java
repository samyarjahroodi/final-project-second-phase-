package finalproject.finalproject.service;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.service.dto.request.SearchForPerson;

import java.util.List;

@SuppressWarnings("unused")
public interface SearchPersonService extends PersonService<Person> {

    List<Person> searchForUsers(SearchForPerson search);
}