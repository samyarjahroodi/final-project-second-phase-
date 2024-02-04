package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.InvalidDataAccessApiUsageException;


import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
    void createDutyForTest() {
        DutyDtoRequest dutyDto = createDutyDto();
        Duty duty = adminService.createDuty(dutyDto);
        assertEquals("home maintenance", duty.getName());
    }

    @Test
    void testCreateDutyWithNullDto() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> adminService.createDuty(null)
        );
        assertEquals("dto cannot be null", exception.getMessage());
    }

    @Test
    void duplicateDutyForTest() {
        adminService.createDuty(createDutyDto());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class
                , () -> adminService.createDuty(createDutyDto()));
        assertEquals("You already have this duty", exception.getMessage());
    }

    @Test
    void createSubDutyForTest() {
        DutyDtoRequest dutyDto = createDutyDto();
        Duty duty = adminService.createDuty(dutyDto);
        SubDutyDtoRequest subDutyDto = createSubDutyDto(3000);
        SubDuty subDuty = adminService.createSubDuty(subDutyDto, duty);
        assertEquals("painting walls", subDuty.getName());
    }

    @Test
    void testCreateSubDutyWithNullDto() {
        Duty duty = createDuty();
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> adminService.createSubDuty(null, duty)
        );
        assertEquals("dto cannot be null", exception.getMessage());
    }



    @Test
    void createDuplicateSubDutyForTest() {
        Duty duty = adminService.createDuty(createDutyDto());
        adminService.createSubDuty(createSubDutyDto(3000), duty);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class
                , () -> adminService.createSubDuty(createSubDutyDto(3000), duty));
        assertEquals("you already have this sub duty", exception.getMessage());
    }

    @Test
    void createSubDutyForTestNotFoundDuty() {
        DutyDtoRequest dutyDto = createDutyDto();
        SubDutyDtoRequest subDutyDto = createSubDutyDto(300);
        Duty duty = Duty.builder()
                .name("ware house color")
                .build();
        InvalidDataAccessApiUsageException invalidDataAccessApiUsageException =
                assertThrows(InvalidDataAccessApiUsageException.class, () -> adminService.createSubDuty(subDutyDto, duty));
        assertEquals("The given id must not be null", invalidDataAccessApiUsageException.getMessage());
    }

    @Test
    void deleteDuty() {
        Duty duty = createDuty();
        adminService.deleteDuty(duty);
        assertEquals(0, dutyRepository.findAll().size());
    }

    @Test
    void testShowDuties() {
        createDuty();
        int size = adminService.showDuties().size();
        assertEquals(1, size);
    }

    @Test
    void testShowSubDuties() {
        createSubDuty(1000);
        int size = adminService.showSubDuties().size();
        assertEquals(1, size);
    }

    @Test
    void updateDetailsForSubDuty() {
        SubDuty subDuty = createSubDuty(2000);
        SubDutyDtoRequest subDutyDto = createSubDutyDto(4000);
        adminService.updateDetailsForSubDuty(subDutyDto, subDuty);
        assertEquals(4000, subDuty.getPrice());
    }

    @Test
    void updateDetailsForSubDutyWithNullSubDutyDto() {
        SubDuty subDuty = createSubDuty(3000);
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class
                , () -> adminService.updateDetailsForSubDuty(null, subDuty));
        assertEquals("Sub duty or dto not found", noSuchElementException.getMessage());
    }

    @Test
    void updateDetailsForSubDutyWithNullSubDuty() {
        SubDutyDtoRequest subDutyDto = createSubDutyDto(3000);
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class
                , () -> adminService.updateDetailsForSubDuty(subDutyDto, null));
        assertEquals("Sub duty or dto not found", noSuchElementException.getMessage());
    }

    @Test
    void createExpertForTest() throws IOException {
        ExpertDtoRequest expertDto = createExpertDto();
        adminService.createExpert(expertDto);
        assertEquals(1, expertRepository.findAll().size());
    }

    @Test
    void createExpertForTestWithNullExpertDto() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class
                , () -> adminService.createExpert(null));
        assertEquals("dto cannot be null", illegalArgumentException.getMessage());
    }


    @Test
    void addSubDutyToDutyByAdmin() {
        SubDuty subDuty = createSubDuty(2000);
        Duty duty = createDuty();
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        assertEquals(1, duty.getSubDuties().size());
    }

    @Test
    void testAddSubDutyToDutyByAdminWhenDutyIsNull() {

        List<SubDuty> subDuties = new ArrayList<>();
        subDuties.add(createSubDuty(3000));

        assertThrows(IllegalArgumentException.class, () ->
                adminService.addSubDutyToDutyByAdmin(null, subDuties), "Duty or sub-duties cannot be null or empty");
    }

    @Test
    void testAddSubDutyToDutyByAdminWhenSubDutiesIsNull() {
        Duty duty = createDuty();
        assertThrows(IllegalArgumentException.class
                , () -> adminService.addSubDutyToDutyByAdmin(duty, null)
                , "Duty or sub-duties cannot be null or empty");
    }


    @Test
    void deleteSubDutyFromTheExistDuty() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        adminService.deleteSubDutyFromTheExistDuty(subDuty);
        assertNull(subDuty.getDuty());
    }

    @Test
    void testDeleteSubDutyFromTheExistDutyWithNullSubDuty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> adminService.deleteSubDutyFromTheExistDuty(null)
        );
        assertEquals("sub duty cannot be null", exception.getMessage());
    }

    @Test
    void deleteSubDutyOFTheSpecificExpert() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(4000);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        adminService.deleteSubDutyOFTheSpecificExpert(expert, subDuty);
        assertEquals(0, subDuty.getExperts().size());
    }

    @Test
    void testDeleteSubDutyOFTheSpecificExpertWithNullExpert() {
        SubDuty subDuty = createSubDuty(3000);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> adminService.deleteSubDutyOFTheSpecificExpert(null, subDuty)
        );
        assertEquals("Expert or sub duty cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteSubDutyOFTheSpecificExpertWithNullSubDuty() throws IOException {
        Expert expert = createExpert();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> adminService.deleteSubDutyOFTheSpecificExpert(expert, null)
        );
        assertEquals("Expert or sub duty cannot be null", exception.getMessage());
    }

    @Test
    void deleteExpertForTest() throws IOException {
        Expert expert = createExpert();
        adminService.deleteExpert(expert);
        assertEquals(0, expertRepository.findAll().size());
    }

    @Test
    void testDeleteExpertWithNullExpert() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> adminService.deleteExpert(null)
        );
        assertEquals("Expert cannot be null", exception.getMessage());
    }

    @Test
    void addSubDutyToNewExpert() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(4000);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        assertEquals(expert, subDuty.getExperts().get(0));
    }

    @Test
    void testAddSubDutyToDutyByAdminWithNullDuty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> adminService.addSubDutyToDutyByAdmin(null, Collections.singletonList(createSubDuty(3000)))
        );
        assertEquals("Duty or sub-duties cannot be null or empty", exception.getMessage());
    }

    @Test
    void testAddSubDutyToNewExpertByAdminWithNullSubDuty() throws IOException {
        Expert expert = createExpert();
        IllegalArgumentException illegalArgumentException
                = assertThrows(IllegalArgumentException.class, () -> adminService.addSubDutyToNewExpert(expert, null));
        assertEquals("Expert or sub duty cannot be null or empty", illegalArgumentException.getMessage());
    }

    @Test
    void testAddSubDutyToNewExpertByAdminWithNullDutyWhenSubDutyHasExpert() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(3000);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        Expert anotherExpert = createAnotherExpert();
        adminService.addSubDutyToNewExpert(anotherExpert, subDuty);
        assertTrue(subDuty.getExperts().contains(anotherExpert));
    }

    @Test
    void testChangeTheStatusOfExpertWithNullExpert() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> expertService.updateRegistrationStatusForSpecificExpert(null)
        );
        assertEquals("Expert must not be null", exception.getMessage());
    }

    @Test
    void changeTheStatusOfExpert() throws IOException {
        Expert expert = createExpert();
        expertService.updateRegistrationStatusForSpecificExpert(expert);
        Assertions.assertEquals(RegistrationStatus.ACCEPTED, expert.getRegistrationStatus());
    }

    @Test
    void changeTheStatusOfExpertWithNullExpert() {
        IllegalArgumentException illegalArgumentException
                = assertThrows(IllegalArgumentException.class, () -> adminService.changeTheStatusOfExpert(null));
        assertEquals("Expert object cannot be null", illegalArgumentException.getMessage());
    }

}