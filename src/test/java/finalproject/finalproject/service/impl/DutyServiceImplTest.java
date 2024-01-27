package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "finalproject.finalproject")
@DataJpaTest
class DutyServiceImplTest extends BaseTest {

    @Test
    void showSubDutiesOfSpecificDuty() {
        Duty duty = createDuty();

    }
}