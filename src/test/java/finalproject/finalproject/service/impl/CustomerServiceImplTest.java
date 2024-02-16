//package finalproject.finalproject.service.impl;
//
//import finalproject.finalproject.Entity.duty.Duty;
//import finalproject.finalproject.Entity.duty.SubDuty;
//import finalproject.finalproject.Entity.operation.CustomerOrder;
//import finalproject.finalproject.Entity.operation.Status;
//import finalproject.finalproject.Entity.operation.Suggestion;
//import finalproject.finalproject.Entity.user.Customer;
//import finalproject.finalproject.Entity.user.Expert;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.ComponentScan;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//
//@DataJpaTest
//@ComponentScan(basePackages = "finalproject.finalproject")
//class CustomerServiceImplTest extends BaseTest {
//
//    @Test
//    void findByUsernameAndPassword() {
//        Customer customer = createCustomer();
//        Customer foundedCustomerByUsernameAndPassword = customerService.findByUsernameAndPassword(customer.getUsername(), customer.getPassword());
//        Assertions.assertEquals(customer, foundedCustomerByUsernameAndPassword);
//    }
//
//    @Test
//    void findByUsernameAndPasswordWhenUsernameOrPasswordIsNull() {
//        Customer customer = createCustomer();
//        IllegalArgumentException illegalArgumentException =
//                Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.findByUsernameAndPassword(null, customer.getPassword()));
//        Assertions.assertEquals("username or password cannot be null", illegalArgumentException.getMessage());
//    }
//
//    @Test
//    void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace() {
//        Duty duty = createDuty();
//        SubDuty subDuty = createSubDuty(1500);
//        CustomerOrder customerOrder =
//                createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
//        customerService.changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(customerOrder);
//        Assertions.assertEquals(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE, customerOrder.getStatus());
//    }
//
//    @Test
//    void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlaceWhenCustomerIsNull() {
//        IllegalArgumentException illegalArgumentException =
//                Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(null));
//        Assertions.assertEquals("customerOrder cannot be null", illegalArgumentException.getMessage());
//    }
//
//    @Test
//    void changeStatusOfCustomerOrderToFinished() {
//        Duty duty = createDuty();
//        SubDuty subDuty = createSubDuty(1500);
//        CustomerOrder customerOrder =
//                createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
//        LocalDate localDate = LocalDate.of(2024, 02, 9);
//        customerService.changeStatusOfCustomerOrderToFinished(customerOrder, ZonedDateTime.from(localDate));
//        Assertions.assertEquals(Status.FINISHED, customerOrder.getStatus());
//    }
//
//    @Test
//    void changeStatusOfCustomerOrderToFinishedWhenCustomerIsNull() {
//        LocalDate localDate = LocalDate.of(2024, 02, 9);
//        IllegalArgumentException illegalArgumentException =
//                Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.changeStatusOfCustomerOrderToFinished(null, ZonedDateTime.from(localDate)));
//        Assertions.assertEquals("customerOrder cannot be null", illegalArgumentException.getMessage());
//    }
//
//
//    @Test
//    void createCustomerForTest() throws MessagingException {
//        customerService.createCustomer(createUserDto(),"jahroosi");
//        Assertions.assertEquals(1, customerRepository.findAll().size());
//    }
//
//    @Test
//    void createCustomerForTestWhenDtoIsNull() {
//        IllegalArgumentException illegalArgumentException =
//                Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(null));
//        Assertions.assertEquals("dto cannot be null", illegalArgumentException.getMessage());
//    }
//
//    @Test
//    void changePassword() {
//        Customer customer = createCustomer();
//        customerService.changePassword(customer.getUsername(), customer.getPassword(), "my_password@domain");
//        Assertions.assertEquals("my_password@domain", customer.getPassword());
//    }
//
//    @Test
//    void changePasswordWhenUsernameOrOldPasswordOrNewPasswordIsNull() {
//        Customer customer = createCustomer();
//        IllegalArgumentException illegalArgumentException =
//                Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.changePassword(null, customer.getPassword(), null));
//        Assertions.assertEquals("username , old password , new password cannot be null", illegalArgumentException.getMessage());
//    }
//
//    @Test
//    public void changeStatusToStarted() throws IOException {
//        Expert expert = createExpert();
//        Duty duty = createDuty();
//        SubDuty subDuty = createSubDuty(2000);
//        Customer customer = createCustomer();
//        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
//        adminService.addSubDutyToNewExpert(expert, subDuty);
//        CustomerOrder customerOrder = createCustomerOrder(2500, LocalDate.now(),
//                Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS, LocalDate.of(2024, 10, 12), subDuty.getPrice(), duty, subDuty);
//        customerOrder.setCustomer(customer);
//        customerOrderRepository.save(customerOrder);
//        Suggestion suggestion = createSuggestion(3100, LocalDate.of(2024, 10, 9));
//        List<Suggestion> suggestionsListForExpert = new ArrayList<>();
//        suggestionsListForExpert.add(suggestion);
//        expert.setSuggestions(suggestionsListForExpert);
//        expertRepository.save(expert);
//        suggestion.setOrder(customerOrder);
//        suggestion.setExpert(expert);
//        suggestionRepository.save(suggestion);
//
//        // Valid scenario
//        LocalDate timeToStartTheProject = LocalDate.of(2024, 10, 10);
//        customerOrder.setStatus(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);
//        suggestion.setWhenSuggestionCreated(LocalDate.of(2024, 10, 8));
//        customerService.changeStatusToStarted(customerOrder, suggestion, timeToStartTheProject);
//        assertEquals(Status.STARTED, customerOrder.getStatus(), "Status should be STARTED");
//
//        // When suggestion created time is after timeToStartTheProject
//        LocalDate invalidTimeToStartTheProject = LocalDate.of(2024, 10, 7);
//        customerOrder.setStatus(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);
//        suggestion.setWhenSuggestionCreated(LocalDate.of(2024, 10, 8));
//        assertThrows(IllegalArgumentException.class, () -> customerService.changeStatusToStarted(customerOrder, suggestion, invalidTimeToStartTheProject),
//                "Should throw IllegalArgumentException for invalid timeToStartTheProject");
//
//        // Null parameters
//        assertThrows(IllegalArgumentException.class, () -> customerService.changeStatusToStarted(null, suggestion, timeToStartTheProject),
//                "customerOrder,suggestion,time to start the project cannot be null");
//        assertThrows(IllegalArgumentException.class, () -> customerService.changeStatusToStarted(customerOrder, null, timeToStartTheProject),
//                "customerOrder,suggestion,time to start the project cannot be null");
//        assertThrows(IllegalArgumentException.class, () -> customerService.changeStatusToStarted(customerOrder, suggestion, null),
//                "customerOrder,suggestion,time to start the project cannot be null");
//    }
//}