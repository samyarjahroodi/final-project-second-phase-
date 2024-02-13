package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.mapper.CustomerMapper;
import finalproject.finalproject.mapper.CustomerOrderMapper;
import finalproject.finalproject.mapper.SuggestionMapper;
import finalproject.finalproject.service.dto.request.*;
import finalproject.finalproject.service.dto.response.*;
import finalproject.finalproject.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerOrderServiceImpl customerOrderService;
    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerServiceImpl customerService;
    private final DutyServiceImpl dutyService;
    private final SubDutyServiceImpl subDutyService;
    private final ExpertServiceImpl expertService;
    private final ModelMapper modelMapper;
    private final CardServiceImpl cardService;
    private final CommentServiceImpl commentService;
    private final SuggestionServiceImpl suggestionService;


    @PostMapping("/create-Customer")
    public ResponseEntity<UserDtoResponse> createCustomer(@RequestBody UserDtoRequest dto) {
        Customer customer = CustomerMapper.INSTANCE.requestDtoToModel(dto);
        customerService.createCustomer(dto);
        UserDtoResponse userDtoResponse = modelMapper.map(customer, UserDtoResponse.class);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/find-By-Username-And-Password")
    public ResponseEntity<UserDtoResponseToLogin> findByUsernameAndPassword(@RequestBody UserDtoRequestToLogin dto) {
        CustomerMapper.INSTANCE.requestDtoToModelToLogin(dto);
        Customer byUsernameAndPassword = customerService.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        UserDtoResponseToLogin userDtoResponseToLogin = modelMapper.map(byUsernameAndPassword, UserDtoResponseToLogin.class);
        return new ResponseEntity<>(userDtoResponseToLogin, HttpStatus.FOUND);
    }

    @PutMapping("/change-Password")
    public ResponseEntity<UserDtoResponseToChangePassword> changePassword(@RequestBody UserDtoChangePasswordRequest dto) {
        Customer customer = CustomerMapper.INSTANCE.requestDtoToModelToChangePassword(dto);
        customerService.changePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
        UserDtoResponseToChangePassword userDtoResponse = modelMapper.map(customer, UserDtoResponseToChangePassword.class);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }


    @PutMapping("/change-status-to-started/{customerOrderId}/{suggestionId}")
    public ResponseEntity<String> changeStatusToStarted(@PathVariable Integer customerOrderId,
                                                        @PathVariable Integer suggestionId) {
        CustomerOrder customerById = customerOrderService.getReferenceById(customerOrderId);
        Suggestion suggestionById = suggestionService.getReferenceById(suggestionId);
        customerService.changeStatusToStarted(customerById, suggestionById, LocalDate.now());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/change-Status-Of-Customer-Order-To-Waiting-For-The-Expert-To-Come-To-Your-Place/{customerOrderId}")
    public ResponseEntity<String> changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        customerService.changeStatusOfCustomerOrderToWaitingForTheExpertToComeToYourPlace(customerOrderById);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/change-Status-Of-Customer-Order-To-Finished/{customerOrderId}")
    public ResponseEntity<String> changeStatusOfCustomerOrderToFinished(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 2, 14, 0, 0, 0, 0, ZoneId.systemDefault());
        customerService.changeStatusOfCustomerOrderToFinished(customerOrderById, zonedDateTime);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/pay-The-Price-Of-Customer-Order-By-Wallet")
    public ResponseEntity<String> payThePriceOfCustomerOrderByWallet(@RequestParam Integer customerOrderId) {
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

    @PostMapping("/payment")
    public ResponseEntity<String> processPayment(@RequestBody CardDtoRequest dto) {
        cardService.createCard(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/publish-Order/{customerId}/{dutyId}/{subDutyId}")
    public ResponseEntity<CustomerOrderDtoResponse> publishOrder(@PathVariable Integer customerId, @RequestBody CustomerOrderDtoRequest dto,
                                                                 @PathVariable Integer dutyId, @PathVariable Integer subDutyId) {
        CustomerOrder customerOrder = customerOrderMapper.INSTANCE.requestDtoToModel(dto);
        Customer customerById = customerService.getReferenceById(customerId);
        Duty dutyById = dutyService.getReferenceById(dutyId);
        SubDuty subDutyById = subDutyService.getReferenceById(subDutyId);
        customerOrderService.publishOrder(customerById, dto, dutyById, subDutyById);
        CustomerOrderDtoResponse customerOrderDtoResponse = modelMapper.map(customerOrder, CustomerOrderDtoResponse.class);
        return new ResponseEntity<>(customerOrderDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/show-Customer-Order-OfSpecific-Customer-Based-On-Price-Of-Suggestions/{customerId}")
    public List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(@PathVariable Integer customerId) {
        Customer customerById = customerService.getReferenceById(customerId);
        return customerOrderService.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customerById);
    }

    @GetMapping("/show-Suggestions-Based-On-Star-Of-Expert/{customerId}")
    public List<Suggestion> showSuggestionsBasedOnStarOfExpert(@PathVariable Integer customerId) {
        Customer customerById = customerService.getReferenceById(customerId);
        return customerOrderService.showSuggestionsBasedOnStarOfExpert(customerById);
    }

    @PutMapping("/reduce-Stars-Of-Expert-If-Needed/{customerId}")
    public ResponseEntity<String> reduceStarsOfExpertIfNeeded(@PathVariable Integer customerId) {
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerId);
        customerOrderService.reduceStarsOfExpertIfNeeded(customerOrderById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/approve-Suggestion/{suggestionId}")
    public ResponseEntity<String> approveSuggestion(@PathVariable Integer suggestionId) {
        Suggestion suggestionById = suggestionService.getReferenceById(suggestionId);
        suggestionService.approveSuggestion(suggestionById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

