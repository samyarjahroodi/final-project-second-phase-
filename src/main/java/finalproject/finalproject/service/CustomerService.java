package finalproject.finalproject.service;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface CustomerService extends BaseService<Customer, Integer> {

    Customer findByUsernameAndPassword(String username, String password);

    void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(CustomerOrder customerOrder);

    void changeStatusToStarted(CustomerOrder customerOrder, Suggestion suggestion, LocalDate timeToStartTheProject);

    void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder);

    Customer createCustomer(UserDtoRequest dto);

    String changePassword(String username, String oldPassword, String password);


    void payThePriceOfCustomerOrderByWallet(CustomerOrder customerOrder);

}
