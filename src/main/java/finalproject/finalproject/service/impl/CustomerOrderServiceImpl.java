package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.repository.CustomerOrderRepository;
import finalproject.finalproject.service.CustomerOrderService;
import finalproject.finalproject.service.dto.CustomerOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl
        implements CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrder publishOrder(Customer customer, CustomerOrderDto dto, Duty duty, SubDuty subDuty) {
        validatePrice(subDuty, dto.getPrice());
        validateExpectedTime(dto.getTimeOfOrder(), dto.getSuggestedTimeToStartTheProjectByCustomer());

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDescription(dto.getDescription());
        customerOrder.setAddress(dto.getAddress());
        subDuty.setPrice(dto.getPrice());
        customerOrder.setDuty(duty);
        customerOrder.setSubDuty(subDuty);
        customerOrder.setTimeOfOrder(dto.getTimeOfOrder());
        customerOrder.setSuggestedTimeToStartTheProjectByCustomer(dto.getSuggestedTimeToStartTheProjectByCustomer());
        customerOrder.setStatus(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);
        customerOrder.setCustomer(customer);
        return customerOrderRepository.save(customerOrder);
    }

    private void validatePrice(SubDuty subDuty, double price) {
        if (subDuty.getPrice() > price) {
            throw new IllegalArgumentException("Your price is not enough");
        }
    }

    private void validateExpectedTime(LocalDate timeOfOrder, LocalDate suggestedTimeToStart) {
        if (timeOfOrder.isAfter(suggestedTimeToStart)) {
            throw new IllegalArgumentException("Your expected time to start is before the current time");
        }
    }

    @Override
    public List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(Expert expert) {
        return customerOrderRepository.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expert);
    }

    @Override
    public List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(Customer customer) {
        return customerOrderRepository.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customer);
    }

    @Override
    public List<Suggestion> showSuggestionsBasedOnStarOfExpert(Customer customer) {
        return customerOrderRepository.showSuggestionsBasedOnStarOfExpert(customer);
    }
}