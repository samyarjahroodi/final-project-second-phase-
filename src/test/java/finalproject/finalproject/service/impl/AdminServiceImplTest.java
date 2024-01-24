package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.service.dto.DutyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private DutyRepository dutyRepository;


    @Test
    @Order(1)
    void itShouldCreateDuty() {
        //given
        DutyDto dto = new DutyDto();
        dto.setName("home maintenance");
        adminService.createDuty(dto);
        Optional<Duty> byId = dutyRepository.findById(1);
        Duty duty = byId.get();
        Assertions.assertEquals(1,duty.getId());
    }

    @Test
    @Order(2)
    void deleteDuty() {

    }

    @Test
    void updateDetailsForSubDuty() {
    }

    @Test
    void createExpert() {
    }

    @Test
    void deleteSubDutyFromTheExistDuty() {
    }

    @Test
    void deleteExpert() {
    }

    @Test
    void createSubDuty() {
    }

    @Test
    void addSubDutyToNewExpert() {
    }

    @Test
    void builder() {
    }
}