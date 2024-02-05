package finalproject.finalproject.service;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import org.springframework.stereotype.Service;

@Service
public interface SuggestionService  extends BaseService<Suggestion,Integer>{
    void approveSuggestion(Suggestion suggestion);

    void createSuggestionForExpert(Expert expert, SuggestionDtoRequest dto, CustomerOrder customerOrder) throws Exception;
}
