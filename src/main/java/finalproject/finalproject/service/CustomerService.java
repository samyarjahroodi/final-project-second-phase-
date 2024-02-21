package finalproject.finalproject.service;

import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public interface CustomerService extends BaseService<Customer, Integer> {

    Customer findByUsernameAndPassword(String username, String password);

    void changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(CustomerOrder customerOrder);

    void changeStatusToStarted(CustomerOrder customerOrder, Suggestion suggestion, LocalDate timeToStartTheProject);

    void changeStatusOfCustomerOrderToFinished(CustomerOrder customerOrder, ZonedDateTime zonedDateTime);

    Customer createCustomer(UserDtoRequest dto, String siteURL) throws MessagingException;

    String changePassword(String username, String oldPassword, String password);

    void payThePriceOfCustomerOrderByWallet(CustomerOrder customerOrder);

    void payThePriceOfCustomerOrderOnline(CustomerOrder customerOrder, Card card);

    List<CustomerOrder> showAllOrders(Customer customer);


}
