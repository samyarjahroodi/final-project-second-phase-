package finalproject.finalproject.service;


import finalproject.finalproject.Entity.operation.Suggestion;
import org.springframework.stereotype.Service;

@Service
public interface SuggestionService {
    /*void approveSuggestion(CustomerOrder customerOrder, Customer customer);*/

    void approveSuggestion(Suggestion suggestion);
}
