package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.CustomerOrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class CustomerOrderServiceImplTest extends BaseTest {

    @Test
    void publishOrder() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        Customer customer = createCustomer();
        CustomerOrderDto customerOrderDto = createCustomerOrderDto(3000, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);
        customerOrderService.publishOrder(customer, customerOrderDto, duty, subDuty);
        assertEquals(1, customerOrderRepository.findAll().size());
    }

    @Test
    void publishOrderWhenCustomerIsNull() {
        Customer customer = createCustomer();
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        NullPointerException nullPointerException = assertThrows(NullPointerException.class,
                () -> customerOrderService.publishOrder(customer, null, duty, subDuty));
        assertEquals("customer , duty , subDuty or dto cannot be null", nullPointerException.getMessage());
    }

    @Test
    void publishOrderWhenTheTimeOfOrderIsBeforeNow() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        Customer customer = createCustomer();
        CustomerOrderDto customerOrderDto = createCustomerOrderDto(3000, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2022, 10, 12), subDuty.getPrice(), duty, subDuty);

        IllegalArgumentException illegalArgumentException
                = assertThrows(IllegalArgumentException.class, () -> customerOrderService.validateExpectedTime(customerOrderDto));
        assertEquals("Your expected time to start is before the current time", illegalArgumentException.getMessage());
    }


    @Test
    void publishOrderWhenThePriceIsLowerThanSubDutyPrice() {
        //In this method we are checking the price that is lower than expected price for sub duty or not
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        Customer customer = createCustomer();
        CustomerOrderDto customerOrderDto = createCustomerOrderDto(2500, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2022, 10, 12), subDuty.getPrice(), duty, subDuty);
        assertThrows(IllegalArgumentException.class, () -> customerOrderService.publishOrder(customer, customerOrderDto, duty, subDuty));
    }

    @Test
    void showCustomerOrdersToExpertBasedOnCustomerOrderStatus() throws IOException {
        Expert expert = createExpert();
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);

        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        adminService.addSubDutyToNewExpert(expert, subDuty);

        //creating customer order based on status
        CustomerOrder customerOrder = createCustomerOrder(2500, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);

        customerOrderRepository.save(customerOrder);
        List<CustomerOrder> customerOrders = customerOrderService.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expert);
        assertEquals(1, customerOrders.size());
    }

    @Test
    void showCustomerOrdersToExpertBasedOnCustomerOrderStatusWhenExpertIsNull() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class
                , () -> customerOrderService.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(null));
        assertEquals("Expert cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestionsWhenCustomerIsNull() {
        IllegalArgumentException illegalArgumentException
                = assertThrows(IllegalArgumentException.class, () -> customerOrderService.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(null));
        assertEquals("Customer cannot be null",illegalArgumentException.getMessage());
    }

    @Test
    void showSuggestionsBasedOnStarOfExpertWhenCustomerIsNull() {
        IllegalArgumentException illegalArgumentException
                = assertThrows(IllegalArgumentException.class, () -> customerOrderService.showSuggestionsBasedOnStarOfExpert(null));
        assertEquals("Customer cannot be null",illegalArgumentException.getMessage());
    }

    @Test
    void showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions() {

        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);
        Customer customer = createCustomer();
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));

        CustomerOrder customerOrder = createCustomerOrder(2500, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);
        customerOrder.setCustomer(customer);

        // Create suggestions for expert
        Suggestion suggestion = createSuggestion(3100, LocalDate.of(2024, 10, 9));
        List<Suggestion> suggestionsListForExpert = new ArrayList<>();
        suggestionsListForExpert.add(suggestion);
        suggestion.setOrder(customerOrder);
        suggestionRepository.save(suggestion);

        // Create suggestions for another expert
        Suggestion suggestion1 = createSuggestion(3200, LocalDate.of(2024, 10, 9));
        List<Suggestion> suggestionsListForAnotherExpert = new ArrayList<>();
        suggestionsListForAnotherExpert.add(suggestion1);
        suggestion1.setOrder(customerOrder);
        suggestionRepository.save(suggestion1);

        // Retrieve suggestions
        List<Suggestion> suggestions = customerOrderService.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customer);
        assertEquals(suggestionsListForExpert.get(0), suggestions.get(0));
    }

    @Test
    void showCustomerOrderOfSpecificCustomerBasedOnStarOfExpert() throws IOException {
        Expert expert = createExpert();
        expert.setStar(4.2);
        expertRepository.save(expert);

        Expert anotherExpert = createAnotherExpert();
        anotherExpert.setStar(4.1);
        expertRepository.save(anotherExpert);

        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);
        Customer customer = createCustomer();
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.addSubDutyToNewExpert(anotherExpert, subDuty);

        CustomerOrder customerOrder = createCustomerOrder(2500, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);
        customerOrder.setCustomer(customer);
        customerOrderRepository.save(customerOrder);

        // Create suggestions for expert
        Suggestion suggestion = createSuggestion(3100, LocalDate.of(2024, 10, 9));
        List<Suggestion> suggestionsListForExpert = new ArrayList<>();
        suggestionsListForExpert.add(suggestion);
        expert.setSuggestions(suggestionsListForExpert);
        expertRepository.save(expert);
        suggestion.setOrder(customerOrder);
        suggestion.setExpert(expert);
        suggestionRepository.save(suggestion);

        // Create suggestions for another expert
        Suggestion suggestion1 = createSuggestion(3100, LocalDate.of(2024, 10, 9));
        List<Suggestion> suggestionsListForAnotherExpert = new ArrayList<>();
        suggestionsListForAnotherExpert.add(suggestion1);
        anotherExpert.setSuggestions(suggestionsListForAnotherExpert);
        expertRepository.save(anotherExpert);
        suggestion1.setOrder(customerOrder);
        suggestion1.setExpert(anotherExpert);
        suggestionRepository.save(suggestion1);

        // Retrieve suggestions
        List<Suggestion> suggestions = customerOrderService.showSuggestionsBasedOnStarOfExpert(customer);
        assertEquals(suggestionsListForExpert.get(0), suggestions.get(0));

    }

}