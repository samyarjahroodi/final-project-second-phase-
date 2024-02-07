package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.exception.*;
import finalproject.finalproject.repository.SuggestionRepository;
import finalproject.finalproject.service.SuggestionService;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static finalproject.finalproject.Entity.operation.Status.WAITING_EXPERT_SELECTION;
import static finalproject.finalproject.Entity.operation.Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl
        implements SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final CustomerOrderServiceImpl customerOrderService;

    @Override
    public void createSuggestionForExpert(Expert expert, SuggestionDtoRequest dto, CustomerOrder customerOrder) throws Exception {
        if (isThereAnyApproveSuggestion(customerOrder)) {
            throw new StatusException("This suggestion has already been approved");
        }
        List<SubDuty> subDuties = suggestionRepository.giveSubDutiesOfExpert(expert);
        if (!subDuties.contains(customerOrder.getSubDuty())) {
            throw new PermissionException("you dont have this permission to create a suggestion because you dont have this sub duty");
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
                throw new PriceException("your price is less than the expected price for this service");
            }
            if (suggestion.getSuggestedTimeToStartTheProject().isBefore(LocalDate.now())) {
                throw new TimeException("your time to start the project is before now");
            }
            customerOrder.setStatus(WAITING_EXPERT_SELECTION);
            customerOrderService.save(customerOrder);
            suggestionRepository.save(suggestion);

        } else {
            throw new StatusException("Whether the status is WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE,STARTED,FINISHED or BEEN_PAID");
        }
    }

    private boolean isThereAnyApproveSuggestion(CustomerOrder customerOrder) {
        for (Suggestion suggestion : customerOrder.getSuggestions()) {
            if (suggestion.getIsApproved().equals(true)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void approveSuggestion(Suggestion suggestion) {
        if (suggestion == null) {
            throw new NullInputException("your suggestion is null");
        }
        suggestion.setIsApproved(true);
        CustomerOrder order = suggestion.getOrder();
        order.setStatus(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE);
        customerOrderService.save(order);
        suggestionRepository.save(suggestion);
    }

    @Override
    public <S extends Suggestion> S save(S entity) {
        return suggestionRepository.save(entity);
    }

    @Override
    public Optional<Suggestion> findById(Integer integer) {
        return suggestionRepository.findById(integer);
    }

    @Override
    public List<Suggestion> findAll() {
        return suggestionRepository.findAll();
    }

    @Override
    public List<Suggestion> findAllById(Iterable<Integer> integers) {
        return suggestionRepository.findAllById(integers);
    }

    @Override
    public List<Suggestion> findAll(Sort sort) {
        return suggestionRepository.findAll(sort);
    }

    @Override
    public Page<Suggestion> findAll(Pageable pageable) {
        return suggestionRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        suggestionRepository.deleteById(integer);
    }

    @Override
    public void delete(Suggestion entity) {
        suggestionRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Suggestion> entities) {
        suggestionRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        suggestionRepository.deleteAll();
    }

    @Override
    public Suggestion getReferenceById(Integer integer) {
        return suggestionRepository.getOne(integer);
    }
}
