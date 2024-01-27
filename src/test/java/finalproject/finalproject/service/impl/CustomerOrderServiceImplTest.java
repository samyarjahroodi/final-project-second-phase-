package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.CustomerOrderDto;
import finalproject.finalproject.service.dto.SuggestionDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.time.LocalDate;
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
        //List<CustomerOrder> all = customerOrderRepository.findAll();
        assertEquals(1, customerOrderRepository.findAll().size());
    }

    @Test
    void publishOrderWhenTheTimeOfOrderIsBeforeNo() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        Customer customer = createCustomer();
        CustomerOrderDto customerOrderDto = createCustomerOrderDto(4000, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2022, 10, 12), subDuty.getPrice(), duty, subDuty);
        assertThrows(IllegalArgumentException.class, () -> customerOrderService.publishOrder(customer, customerOrderDto, duty, subDuty));
    }


    @Test
    void publishOrderWhenThePriceIsLowerThanSubDutyPrice() {
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
        adminService.addSubDutyToDutyByAdmin(duty, subDuty);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        CustomerOrder customerOrder = createCustomerOrder(2500, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);
        SuggestionDto suggestionDto =
                createSuggestionDto(2300, LocalDate.now(), LocalDate.of(2024, 10, 12), 10);
        //suggestionService.createSuggestionForExpert(expert, suggestionDto, customerOrder);
        customerOrderRepository.save(customerOrder);
        List<CustomerOrder> customerOrders = customerOrderService.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expert);
        assertEquals(1, customerOrders.size());
    }

    @Test
    void showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions() {

    }

/*    @Test
    void showCustomerOrderOfSpecificCustomerBasedOnStarOfExpert() throws IOException {
        Expert expert = createExpert();
        expert.setStar(3.7);
        expertRepository.save(expert);
        Expert expert1 = createExpert();
        expert1.setUsername("sajad");
        expert1.setEmail("sajad@example.com");
        expertRepository.save(expert1);
        expert1.setStar(4.1);
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);
        Customer customer = createCustomer();
        adminService.addSubDutyToDutyByAdmin(duty, subDuty);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.addSubDutyToNewExpert(expert1, subDuty);
        CustomerOrder customerOrder = createCustomerOrder(2500, LocalDate.now(),
                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);
        customerOrder.setCustomer(customer);
        Suggestion suggestion = createSuggestion(duty, subDuty);
        expert.setSuggestions(Collections.singletonList(suggestion));
        Suggestion suggestion1 = createSuggestion(duty, subDuty);
        expert1.setSuggestions(Collections.singletonList(suggestion1));
        List<Suggestion> suggestions = customerOrderService.showSuggestionsBasedOnStarOfExpert(customer);
        assertEquals(suggestion1,suggestions.get(0));
    }*/
}