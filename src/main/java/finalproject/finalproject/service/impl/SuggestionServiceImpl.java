package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.repository.SuggestionRepository;
import finalproject.finalproject.service.SuggestionService;
import finalproject.finalproject.service.dto.SuggestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static finalproject.finalproject.Entity.operation.Status.WAITING_EXPERT_SELECTION;
import static finalproject.finalproject.Entity.operation.Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl
        implements SuggestionService {
    private final SuggestionRepository suggestionRepository;

    public void createSuggestionForExpert(Expert expert, SuggestionDto dto, CustomerOrder customerOrder) throws Exception {
        if (suggestionRepository.)
        if (customerOrder.getStatus().equals(WAITING_FOR_THE_SUGGESTION_OF_EXPERTS)
                || customerOrder.getStatus().equals(WAITING_EXPERT_SELECTION)) {
            Suggestion suggestion = Suggestion.builder()
                    .suggestedPrice(dto.getSuggestedPrice())
                    .whenSuggestionCreated(dto.getWhenSuggestionCreated())
                    .daysThatTaken(dto.getDaysThatTaken())
                    .order(customerOrder)
                    .expert(expert)
                    .build();
            if (suggestion.getSuggestedPrice() < customerOrder.getSubDuty().getPrice()) {
                throw new Exception("your price is less than the expected price for this service");
            }

        } else {
            throw new Exception("Whether the status is WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE,STARTED,FINISHED and BEEN_PAID")
        }
    }
}