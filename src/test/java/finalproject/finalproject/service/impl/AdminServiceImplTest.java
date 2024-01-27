package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class AdminServiceImplTest extends BaseTest {


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        dutyRepository.deleteAll();
        subDutyRepository.deleteAll();
    }

    @Test
    @Order(1)
    void createDutyForTest() {
        Duty duty = createDuty();

        assertEquals("home maintenance", duty.getName());
    }

    @Test
    @Order(2)
    void createSubDuty() {
        SubDuty subDuty = createSubDuty(2000);
        assertEquals("painting walls", subDuty.getName());
    }

    @Test
    @Order(3)
    void testShowDuties() {
        createDuty();
        int size = dutyRepository.findAll().size();
        assertEquals(1, size);
    }

    @Test
    void testShowSubDuties() {
        createSubDuty(1000);
        int size = subDutyRepository.findAll().size();
        assertEquals(1, size);
    }

    @Test
    void deleteDuty() {
        Duty duty = createDuty();
        int size = dutyRepository.findAll().size();
        assertEquals(1, size);
        dutyRepository.deleteAll();
        int size1 = dutyRepository.findAll().size();
        assertEquals(0, size1);
    }

    @Test
    void updateDetailsForSubDuty() {
        SubDuty subDuty = createSubDuty(2000);
        subDuty.setPrice(3000);
        subDutyRepository.save(subDuty);
        assertEquals(3000, subDuty.getPrice());
    }

    @Test
    void createExpertForTest() throws IOException {
        Expert expert = createExpert();
        Optional<Expert> byId = expertRepository.findById(1);
        Expert expert1 = byId.get();
        assertEquals(expert, expert1);

    }

    @Test
    void deleteSubDutyFromTheExistDuty() throws IOException {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        adminService.deleteSubDutyFromTheExistDuty(subDuty);
        assertEquals(null, subDuty.getDuty());
    }

    @Test
    void deleteExpertFromSubDuty() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(4000);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.deleteSubDutyOFTheSpecificExpert(expert, subDuty);
        assertEquals(0, subDuty.getExperts().size());
    }

    @Test
    void deleteExpertForTest() throws IOException {
        Expert expert = createExpert();
        adminService.deleteExpert(expert);
        assertEquals(0, expertRepository.findAll().size());
    }

    @Test
    void addSubDutyToNewExpert() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(4000);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        assertEquals(expert,subDuty.getExperts().get(0));
    }
}