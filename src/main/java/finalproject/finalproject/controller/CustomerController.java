package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.Card;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.mapper.CustomerMapper;
import finalproject.finalproject.service.dto.request.CardDtoRequest;
import finalproject.finalproject.service.dto.request.UserDtoChangePasswordRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequestToLogin;
import finalproject.finalproject.service.dto.response.UserDtoResponse;
import finalproject.finalproject.service.dto.response.UserDtoResponseToChangePassword;
import finalproject.finalproject.service.dto.response.UserDtoResponseToLogin;
import finalproject.finalproject.service.impl.CardServiceImpl;
import finalproject.finalproject.service.impl.CustomerOrderServiceImpl;
import finalproject.finalproject.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final ModelMapper modelMapper;
    private final CustomerOrderServiceImpl customerOrderService;
    private final CardServiceImpl cardService;

    @GetMapping("/find-By-Username-And-Password")
    public ResponseEntity<UserDtoResponseToLogin> findByUsernameAndPassword(@RequestBody UserDtoRequestToLogin dto) {
        CustomerMapper.INSTANCE.requestDtoToModelToLogin(dto);
        Customer byUsernameAndPassword = customerService.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        UserDtoResponseToLogin userDtoResponseToLogin = modelMapper.map(byUsernameAndPassword, UserDtoResponseToLogin.class);
        return new ResponseEntity<>(userDtoResponseToLogin, HttpStatus.FOUND);
    }

    @PostMapping("/create-Customer")
    public ResponseEntity<UserDtoResponse> createCustomer(@RequestBody UserDtoRequest dto) {
        Customer customer = CustomerMapper.INSTANCE.requestDtoToModel(dto);
        customerService.createCustomer(dto);
        UserDtoResponse userDtoResponse = modelMapper.map(customer, UserDtoResponse.class);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }

    //todo implement comlete response (password is null)!!!!
    @PutMapping("/change-Password")
    public ResponseEntity<UserDtoResponseToChangePassword> changePassword(@RequestBody UserDtoChangePasswordRequest dto) {
        Customer customer = CustomerMapper.INSTANCE.requestDtoToModelToChangePassword(dto);
        customerService.changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
        UserDtoResponseToChangePassword userDtoResponse = modelMapper.map(customer, UserDtoResponseToChangePassword.class);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @PutMapping("/change-Status-Of-Customer-Order-To-Waiting-For-The-Expert-To-Come-To-Your-Place/{customerOrderId}")
    public ResponseEntity<String> changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        customerService.changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(customerOrderById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo
    @PutMapping("/change-Status-To-Started/{customerOrderId}")
    public ResponseEntity<String> changeStatusToStarted(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        customerService.changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(customerOrderById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-Status-Of-Customer-Order-To-Finished/{customerOrderId}")
    public ResponseEntity<String> changeStatusOfCustomerOrderToFinished(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        LocalDate localDate = LocalDate.of(2024, 02, 9);
        customerService.changeStatusOfCustomerOrderToFinished(customerOrderById, localDate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //todo : complete validation
    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestBody CardDtoRequest dto) {
        String cardNumber = dto.getCardNumber();
        int cvv2 = dto.getCvv2();
        int month = dto.getMonth();
        int year = dto.getYear();
        int password = dto.getPassword();
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setCvv2(cvv2);
        card.setExpireDate(LocalDate.of(year, month, 1));
        card.setPassword(password);
        CustomerOrder customerOrder = customerOrderService.getReferenceById(2);
        customerService.payThePriceOfCustomerOrderOnline(customerOrder, card);
        cardService.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

