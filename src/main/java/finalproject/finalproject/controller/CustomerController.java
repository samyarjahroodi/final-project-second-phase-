package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.mapper.CustomerMapper;
import finalproject.finalproject.service.dto.request.*;
import finalproject.finalproject.service.dto.response.UserDtoResponse;
import finalproject.finalproject.service.dto.response.UserDtoResponseToChangePassword;
import finalproject.finalproject.service.dto.response.UserDtoResponseToLogin;
import finalproject.finalproject.service.impl.CardServiceImpl;
import finalproject.finalproject.service.impl.CommentServiceImpl;
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
    private final CommentServiceImpl commentService;

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

    @PutMapping("/pay-The-Price-Of-Customer-Order-By-Wallet/{customerOrderId}")
    public ResponseEntity<String> payThePriceOfCustomerOrderByWallet(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        customerService.payThePriceOfCustomerOrderByWallet(customerOrderById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-Comment-For-Customer-Id/{customerOrderId}")
    public ResponseEntity<String> addCommentForCustomerId(@PathVariable Integer customerOrderId, @RequestBody CommentDtoRequest dto) {
        CustomerOrder customerOrder = customerOrderService.getReferenceById(customerOrderId);
        commentService.addCommentForCustomerId(customerOrder, dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //todo : complete validation
    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestBody CardDtoRequest dto) {
        Card card = cardService.creatCard(dto);
        CustomerOrder customerOrder = customerOrderService.getReferenceById(1);
        customerService.payThePriceOfCustomerOrderOnline(customerOrder, card);
        cardService.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

