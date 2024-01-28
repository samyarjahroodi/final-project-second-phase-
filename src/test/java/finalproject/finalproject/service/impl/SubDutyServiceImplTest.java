package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class SubDutyServiceImplTest extends BaseTest {
    @Test
    void deleteSubDutyFromTheExistDuty() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(2000);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        subDutyService.deleteSubDutyFromTheExistDuty(subDuty);
        assertNull(subDuty.getDuty());
    }

    @Test
    void testDeleteSubDutyFromTheExistDutyWithNullSubDuty() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> subDutyService.deleteSubDutyFromTheExistDuty(null)
        );
        assertEquals("sub duty cannot be null", exception.getMessage());
    }
}