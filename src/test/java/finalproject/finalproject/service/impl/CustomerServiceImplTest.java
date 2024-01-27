package finalproject.finalproject.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class CustomerServiceImplTest {

    @Test
    void findByUsernameAndPassword() {

    }

    @Test
    void changeStatusOfCustomerOrder() {

    }

    @Test
    void changeStatusOfCustomerOrderToFinished() {

    }

    @Test
    void createCustomer() {

    }

    @Test
    void changePassword() {

    }
}