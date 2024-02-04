package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;

import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class SuggestionServiceImplTest extends BaseTest {

    @AfterEach
    void tearDown() {
        dutyRepository.deleteAll();
        expertRepository.deleteAll();
        customerOrderRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void createSuggestionForExpert() throws Exception {
        SubDuty subDuty = createSubDuty(1000);
        Duty duty = createDuty();
        Expert expert = createExpert();
        adminService.addSubDutyToNewExpert(expert, subDuty);
        addSubDutyToDutyByAdmin(duty, subDuty, 1000);
        CustomerOrder customerOrder = createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        SuggestionDtoRequest suggestionDto =
                createSuggestionDto(1500, LocalDate.now(), LocalDate.of(2024, 10, 10), 10);
        suggestionService.createSuggestionForExpert(expert, suggestionDto, customerOrder);
        assertEquals(1, suggestionRepository.findAll().size());
        // assertEquals(1, customerOrderRepository.findAll().size());
    }

    @Test
    void approveSuggestion() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        Suggestion suggestion = createSuggestion(3100, LocalDate.of(2024, 10, 9)/*,
                3000, LocalDate.of(2024, 10, 9), duty, subDuty*/);
        CustomerOrder order = createCustomerOrder(3500, LocalDate.now(), Status.WAITING_EXPERT_SELECTION,
                LocalDate.of(2024, 10, 9), subDuty.getPrice(), duty, subDuty);
        suggestion.setOrder(order);
        suggestionService.approveSuggestion(suggestion);
        customerOrderRepository.save(order);
        assertEquals(true, suggestion.getIsApproved());
        assertEquals(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE, order.getStatus());
    }

    @Test
    void createSuggestionForExpertWhenPriceIsLessThanSubDuty() throws Exception {
        SubDuty subDuty = createSubDuty(1000);
        Duty duty = createDuty();
        Expert expert = createExpert();
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        CustomerOrder customerOrder = createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        SuggestionDtoRequest suggestionDto =
                createSuggestionDto(900, LocalDate.now(), LocalDate.of(2024, 10, 10), 10);
        Exception exception
                = assertThrows(Exception.class, () -> suggestionService.createSuggestionForExpert(expert, suggestionDto, customerOrder));
        assertEquals("your price is less than the expected price for this service", exception.getMessage());
    }

    @Test
    void createSuggestionForExpertWhenSuggestedTimeIsBeforeNow() throws Exception {
        SubDuty subDuty = createSubDuty(1000);
        Duty duty = createDuty();
        Expert expert = createExpert();
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        CustomerOrder customerOrder = createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        SuggestionDtoRequest suggestionDto =
                createSuggestionDto(9000, LocalDate.now(), LocalDate.of(2022, 10, 10), 10);
        Exception exception
                = assertThrows(Exception.class, () -> suggestionService.createSuggestionForExpert(expert, suggestionDto, customerOrder));
        assertEquals("your time to start the project is before now", exception.getMessage());
    }


    @Test
    void createSuggestionForExpertWhenStatusIsNot_WAITING_EXPERT_SELECTION() throws Exception {
        SubDuty subDuty = createSubDuty(1000);
        Duty duty = createDuty();
        Expert expert = createExpert();
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        CustomerOrder customerOrder = createCustomerOrder(1900, LocalDate.now(), Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        SuggestionDtoRequest suggestionDto =
                createSuggestionDto(9000, LocalDate.now(), LocalDate.of(2022, 10, 10), 10);
        Exception exception
                = assertThrows(Exception.class, () -> suggestionService.createSuggestionForExpert(expert, suggestionDto, customerOrder));
        assertEquals("Whether the status is WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE,STARTED,FINISHED or BEEN_PAID", exception.getMessage());
    }

    @Test
    void createSuggestionForExpertWhenExpertDoesntHavePermission() throws Exception {
        SubDuty subDuty = createSubDuty(1000);
        Duty duty = createDuty();
        Expert expert = createExpert();
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        CustomerOrder customerOrder = createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        SuggestionDtoRequest suggestionDto =
                createSuggestionDto(9000, LocalDate.now(), LocalDate.of(2022, 10, 10), 10);
        Exception exception
                = assertThrows(Exception.class, () -> suggestionService.createSuggestionForExpert(expert, suggestionDto, customerOrder));
        assertEquals("you dont have this permission to create a suggestion because you dont have this sub duty", exception.getMessage());
    }
}