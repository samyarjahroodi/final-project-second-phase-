package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;

import finalproject.finalproject.service.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

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
        SuggestionDto suggestionDto =
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

}