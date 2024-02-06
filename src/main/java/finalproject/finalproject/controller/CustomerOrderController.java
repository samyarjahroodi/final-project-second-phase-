package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.mapper.CustomerOrderMapper;
import finalproject.finalproject.service.dto.request.CustomerOrderDtoRequest;
import finalproject.finalproject.service.dto.response.CustomerOrderDtoResponse;
import finalproject.finalproject.service.impl.*;
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
    private final CustomerServiceImpl customerService;
    private final DutyServiceImpl dutyService;
    private final SubDutyServiceImpl subDutyService;
    private final ExpertServiceImpl expertService;
    private final ModelMapper modelMapper;


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

    //todo see customerOrder and in to string implement duty and subDuty
    @GetMapping("/show-Customer-Orders-To-Expert-Based-On-Customer-Order-Status/{expertId}")
    public List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(@PathVariable Integer expertId) {
        Expert expertById = expertService.getReferenceById(expertId);
        return customerOrderService.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expertById);
    }

    //todo how to solve stack over flow bug for suggestions(expert)
    @GetMapping("/show-Customer-Order-OfSpecific-Customer-Based-On-Price-Of-Suggestions/{customerId}")
    public List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(@PathVariable Integer customerId) {
        Customer customerById = customerService.getReferenceById(customerId);
        return customerOrderService.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customerById);
    }
    //todo how to solve stack over flow bug for suggestions(expert)
    @GetMapping("/show-Suggestions-Based-On-Star-Of-Expert/{customerId}")
    public List<Suggestion> showSuggestionsBasedOnStarOfExpert(@PathVariable Integer customerId) {
        Customer customerById = customerService.getReferenceById(customerId);
        return customerOrderService.showSuggestionsBasedOnStarOfExpert(customerById);
    }
}

