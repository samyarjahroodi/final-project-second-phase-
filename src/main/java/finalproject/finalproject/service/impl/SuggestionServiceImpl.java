package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.repository.CustomerOrderRepository;
import finalproject.finalproject.repository.SuggestionRepository;
import finalproject.finalproject.service.SuggestionService;
import finalproject.finalproject.service.dto.SuggestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

import static finalproject.finalproject.Entity.operation.Status.WAITING_EXPERT_SELECTION;
import static finalproject.finalproject.Entity.operation.Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl
        implements SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public void createSuggestionForExpert(Expert expert, SuggestionDto dto, CustomerOrder customerOrder) throws Exception {
        List<SubDuty> subDuties = suggestionRepository.giveSubDutiesOfExpert(expert);
        if (!subDuties.contains(customerOrder.getSubDuty())) {
            throw new Exception("you dont have this permission to create a suggestion because you dont have this sub duty");
        }
        if (customerOrder.getStatus().equals(WAITING_FOR_THE_SUGGESTION_OF_EXPERTS)
                || customerOrder.getStatus().equals(WAITING_EXPERT_SELECTION)) {
            Suggestion suggestion = Suggestion.builder()
                    .suggestedPrice(dto.getSuggestedPrice())
                    .whenSuggestionCreated(dto.getWhenSuggestionCreated())
                    .daysThatTaken(dto.getDaysThatTaken())
                    .suggestedTimeToStartTheProject(dto.getSuggestedTimeToStartTheProject())
                    .order(customerOrder)
                    .expert(expert)
                    .build();
            if (suggestion.getSuggestedPrice() < customerOrder.getSubDuty().getPrice()) {
                throw new Exception("your price is less than the expected price for this service");
            }
            if (suggestion.getSuggestedTimeToStartTheProject().isBefore(LocalDate.now())) {
                throw new Exception("your time to start the project is before now");
            }
            customerOrder.setStatus(WAITING_EXPERT_SELECTION);
            customerOrderRepository.save(customerOrder);
            suggestionRepository.save(suggestion);

        } else {
            throw new Exception("Whether the status is WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE,STARTED,FINISHED or BEEN_PAID");
        }
    }

    @Override
    public void approveSuggestion(Suggestion suggestion) {
        suggestion.setIsApproved(true);
        CustomerOrder order = suggestion.getOrder();
        order.setStatus(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE);
        customerOrderRepository.save(order);
        suggestionRepository.save(suggestion);
    }

}
