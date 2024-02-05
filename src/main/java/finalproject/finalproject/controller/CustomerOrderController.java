package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.mapper.CustomerOrderMapper;
import finalproject.finalproject.repository.CustomerRepository;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.dto.request.CustomerOrderDtoRequest;
import finalproject.finalproject.service.dto.response.CustomerOrderDtoResponse;
import finalproject.finalproject.service.impl.CustomerOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer-order")
public class CustomerOrderController {
    private final CustomerOrderServiceImpl customerOrderService;
    private final CustomerOrderMapper customerOrderMapper;
    private final CustomerRepository customerRepository;
    private final DutyRepository dutyRepository;
    private final SubDutyRepository subDutyRepository;
    private final ExpertRepository expertRepository;
    private final ModelMapper modelMapper;


    @PostMapping("/publish-Order/{customerId}/{dutyId}/{subDutyId}")
    public ResponseEntity<CustomerOrderDtoResponse> publishOrder(@PathVariable Integer customerId, @RequestBody CustomerOrderDtoRequest dto,
                                                                 @PathVariable Integer dutyId, @PathVariable Integer subDutyId) {
        CustomerOrder customerOrder = customerOrderMapper.INSTANCE.requestDtoToModel(dto);
        Customer customerById = customerRepository.getReferenceById(customerId);
        Duty dutyById = dutyRepository.getReferenceById(dutyId);
        SubDuty subDutyById = subDutyRepository.getReferenceById(subDutyId);
        customerOrderService.publishOrder(customerById, dto, dutyById, subDutyById);
        CustomerOrderDtoResponse customerOrderDtoResponse = modelMapper.map(customerOrder, CustomerOrderDtoResponse.class);
        return new ResponseEntity<>(customerOrderDtoResponse, HttpStatus.CREATED);
    }

    //todo see customerOrder and in to string implement duty and subDuty
    @GetMapping("/show-Customer-Orders-To-Expert-Based-On-Customer-Order-Status/{expertId}")
    public List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(@PathVariable Integer expertId) {
        Expert expertById = expertRepository.getReferenceById(expertId);
        return customerOrderService.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expertById);
    }

    //todo how to solve stack over flow bug for suggestions(expert)
    @GetMapping("/show-Customer-Order-OfSpecific-Customer-Based-On-Price-Of-Suggestions/{customerId}")
    public List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(@PathVariable Integer customerId) {
        Customer customerById = customerRepository.getReferenceById(customerId);
        return customerOrderService.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customerById);
    }
    //todo how to solve stack over flow bug for suggestions(expert)
    @GetMapping("/show-Suggestions-Based-On-Star-Of-Expert/{customerId}")
    public List<Suggestion> showSuggestionsBasedOnStarOfExpert(@PathVariable Integer customerId) {
        Customer customerById = customerRepository.getReferenceById(customerId);
        return customerOrderService.showSuggestionsBasedOnStarOfExpert(customerById);
    }
}

