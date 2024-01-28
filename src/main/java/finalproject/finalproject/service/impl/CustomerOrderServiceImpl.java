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


import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl
        implements CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrder publishOrder(Customer customer, CustomerOrderDto dto, Duty duty, SubDuty subDuty) {
        if (customer == null || duty == null || subDuty == null || dto == null) {
            throw new NullPointerException("customer , duty , subDuty or dto cannot be null");
        }
        validatePrice(subDuty, dto);
        validateExpectedTime(dto);

        CustomerOrder customerOrder = CustomerOrder.builder()
                .description(dto.getDescription())
                .address(dto.getAddress())
                .duty(duty)
                .subDuty(subDuty)
                .price(dto.getPrice())
                .timeOfOrder(dto.getTimeOfOrder())
                .suggestedTimeToStartTheProjectByCustomer(dto.getSuggestedTimeToStartTheProjectByCustomer())
                .status(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS)
                .customer(customer)
                .build();

        return customerOrderRepository.save(customerOrder);
    }

    public void validatePrice(SubDuty subDuty, CustomerOrderDto dto) {
        if (subDuty.getPrice() > dto.getPrice()) {
            throw new IllegalArgumentException("Your price is not enough");
        }
    }

    public void validateExpectedTime(CustomerOrderDto dto) {
        if (dto.getTimeOfOrder().isAfter(dto.getSuggestedTimeToStartTheProjectByCustomer())) {
            throw new IllegalArgumentException("Your expected time to start is before the current time");
        }
    }

    @Override
    public List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(Expert expert) {
        if (expert == null) {
            throw new IllegalArgumentException("Expert cannot be null");
        }
        return customerOrderRepository.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expert);
    }

    @Override
    public List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return customerOrderRepository.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customer);
    }

    @Override
    public List<Suggestion> showSuggestionsBasedOnStarOfExpert(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return customerOrderRepository.showSuggestionsBasedOnStarOfExpert(customer);
    }
}