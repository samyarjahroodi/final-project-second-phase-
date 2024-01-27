package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;
import java.util.List;

@ComponentScan(basePackages = "finalproject.finalproject")
@DataJpaTest
class DutyServiceImplTest extends BaseTest {

    @Test
    void showSubDutiesOfSpecificDuty() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(3000);
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        List<SubDuty> subDuties = dutyService.showSubDutiesOfSpecificDuty(duty);
        Assertions.assertEquals(1, subDuties.size());
    }
}