package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.repository.PersonRepository;
import finalproject.finalproject.service.SearchPersonService;

import finalproject.finalproject.service.dto.request.SearchForPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class SearchPersonServiceImpl
        extends PersonServiceImpl<Person, PersonRepository<Person>>
        implements SearchPersonService {


    public SearchPersonServiceImpl(PersonRepository<Person> repository) {
        super(repository);
    }

    @Override
    public List<Person> searchForUsers(SearchForPerson search) {
        return search(search);
    }


}