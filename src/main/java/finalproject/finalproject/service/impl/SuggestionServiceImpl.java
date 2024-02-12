package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.exception.*;
import finalproject.finalproject.repository.SuggestionRepository;
import finalproject.finalproject.service.SuggestionService;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.util.Arrays;
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
    public void createSuggestionForExpert(Expert expert, SuggestionDtoRequest dto, CustomerOrder customerOrder) {
        validateExpertStatus(expert);
        ensureNoApprovedSuggestions(customerOrder);
        ensureExpertHasSubDuty(expert, customerOrder);

        validateCustomerOrderStatus(customerOrder);

        createSuggestion(expert, dto, customerOrder);
    }

    private void validateExpertStatus(Expert expert) {
        if (!expert.getRegistrationStatus().equals(RegistrationStatus.ACCEPTED)) {
            throw new StatusException("Your status must be accepted");
        }
    }

    private void ensureNoApprovedSuggestions(CustomerOrder customerOrder) {
        if (isThereAnyApprovedSuggestion(customerOrder)) {
            throw new StatusException("This suggestion has already been approved");
        }
    }

    private void ensureExpertHasSubDuty(Expert expert, CustomerOrder customerOrder) {
        List<SubDuty> subDuties = suggestionRepository.giveSubDutiesOfExpert(expert);
        if (!subDuties.contains(customerOrder.getSubDuty())) {
            throw new PermissionException("You don't have permission to create a suggestion because you don't have this subduty");
        }
    }

    private void validateCustomerOrderStatus(CustomerOrder customerOrder) {
        if (!Arrays.asList(WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, WAITING_EXPERT_SELECTION).contains(customerOrder.getStatus())) {
            throw new StatusException("Invalid order status");
        }
    }

    private void createSuggestion(Expert expert, SuggestionDtoRequest dto, CustomerOrder customerOrder) {
        Suggestion suggestion = Suggestion.builder()
                .suggestedPrice(dto.getSuggestedPrice())
                .whenSuggestionCreated(dto.getWhenSuggestionCreated())
                .hoursThatTaken(dto.getHoursThatTaken())
                .suggestedTimeToStartTheProject(dto.getSuggestedTimeToStartTheProject())
                .order(customerOrder)
                .expert(expert)
                .build();

        if (suggestion.getSuggestedPrice() < customerOrder.getSubDuty().getPrice()) {
            throw new PriceException("Your price is less than the expected price for this service");
        }

        if (suggestion.getSuggestedTimeToStartTheProject().isBefore(ZonedDateTime.now())) {
            throw new TimeException("Your time to start the project is before now");
        }

        customerOrder.setStatus(WAITING_EXPERT_SELECTION);
        customerOrderService.save(customerOrder);
        suggestionRepository.save(suggestion);
    }

    private boolean isThereAnyApprovedSuggestion(CustomerOrder customerOrder) {
        return customerOrder.getSuggestions().stream().anyMatch(Suggestion::getIsApproved);
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
        return suggestionRepository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }
}
