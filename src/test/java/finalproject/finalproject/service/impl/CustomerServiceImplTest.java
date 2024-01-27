package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.user.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;


@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class CustomerServiceImplTest extends BaseTest {

    @Test
    void findByUsernameAndPassword() {
        Customer customer = createCustomer();
        Customer foundedCustomerByUsernameAndPassword = customerService.findByUsernameAndPassword(customer.getUsername(),customer.getPassword());
        Assertions.assertEquals(customer,foundedCustomerByUsernameAndPassword);
    }

    @Test
    void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(1500);
        CustomerOrder customerOrder =
                createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        customerService.changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(customerOrder);
        Assertions.assertEquals(Status.WAITING_FOR_THE_EXPERT_TO_COME_TO_YOUR_PLACE, customerOrder.getStatus());
    }

    @Test
    void changeStatusOfCustomerOrderToFinished() {
        Duty duty = createDuty();
        SubDuty subDuty = createSubDuty(1500);
        CustomerOrder customerOrder =
                createCustomerOrder(1900, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, LocalDate.of(2024, 10, 9), 1000, duty, subDuty);
        customerService.changeStatusOfCustomerOrderToFinished(customerOrder);
        Assertions.assertEquals(Status.FINISHED, customerOrder.getStatus());
    }

    @Test
    void createCustomerForTest() {
        Customer customer = createCustomer();
        Assertions.assertEquals(1, customerRepository.findAll().size());
    }

    @Test
    void changePassword() {
        Customer customer = createCustomer();
        customerService.changePassword(customer.getUsername(),customer.getPassword(),"my_password@domain");
        Assertions.assertEquals("my_password@domain",customer.getPassword());
    }
}