package finalproject.finalproject.service;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.request.CustomerOrderDtoRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerOrderService {

    List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(Expert expert);

    List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(Customer customer);

    List<Suggestion> showSuggestionsBasedOnStarOfExpert(Customer customer);

    CustomerOrder publishOrder(Customer customer, CustomerOrderDtoRequest dto, Duty duty, SubDuty subDuty);
}
