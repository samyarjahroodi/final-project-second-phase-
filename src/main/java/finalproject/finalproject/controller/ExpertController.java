package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.mapper.ExpertMapper;
import finalproject.finalproject.mapper.SuggestionMapper;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import finalproject.finalproject.service.dto.response.ExpertDtoResponse;
import finalproject.finalproject.service.dto.response.SuggestionDtoResponse;
import finalproject.finalproject.service.impl.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final CustomerOrderServiceImpl customerOrderService;
    private final ExpertServiceImpl expertService;
    private final ModelMapper modelMapper;
    private final SuggestionServiceImpl suggestionService;

    @PostMapping("/createExpert")
    public ResponseEntity<ExpertDtoResponse> createExpert(@RequestBody @Valid ExpertDtoRequest dto, HttpServletRequest request) throws IOException {
        Expert expert = ExpertMapper.INSTANCE.requestDtoToModel(dto);
        String siteURL = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        expertService.createExpert(dto, siteURL);
        ExpertDtoResponse expertDtoResponse = modelMapper.map(expert, ExpertDtoResponse.class);
        return new ResponseEntity<>(expertDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/verify-expert")
    public ResponseEntity<String> verify(@RequestParam String code) {
        expertService.verify(code);
        return new ResponseEntity<>("Email verified successfully", HttpStatus.OK);
    }

    @GetMapping("/see-wallet-credit/{expertId}")
    public double seeWalletCredit(@PathVariable Integer expertId) {
        Expert expert = expertService.getReferenceById(expertId);
        return expertService.seeWalletCredit(expert);
    }

    @PutMapping("/average-star/{expertId}")
    public void averageStar(@PathVariable Integer expertId) {
        Expert expertById = expertService.getReferenceById(expertId);
        expertService.averageStarOfExpert(expertById);
    }

    @GetMapping("/see-Star-Of-Order/{customerOrderId}")
    public double seeStarOfOrder(@PathVariable Integer customerOrderId) {
        CustomerOrder customerOrderById =
                customerOrderService.getReferenceById(customerOrderId);
        return expertService.seeStarOfOrder(customerOrderById);
    }


    @PostMapping("/create-Suggestion-For-Expert/{expertId}/{customerOrderId}")
    public ResponseEntity<SuggestionDtoResponse> createSuggestionForExpert(@PathVariable Integer expertId
            , @RequestBody SuggestionDtoRequest dto, @PathVariable Integer customerOrderId) throws Exception {
        Suggestion suggestion = SuggestionMapper.INSTANCE.requestDtoToModel(dto);
        Expert expertById = expertService.getReferenceById(expertId);
        CustomerOrder customerOrderById = customerOrderService.getReferenceById(customerOrderId);
        suggestionService.createSuggestionForExpert(expertById, dto, customerOrderById);
        SuggestionDtoResponse suggestionDtoResponse = modelMapper.map(suggestion, SuggestionDtoResponse.class);
        return new ResponseEntity<>(suggestionDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/show-Customer-Orders-To-Expert-Based-On-Customer-Order-Status/{expertId}")
    public List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(@PathVariable Integer expertId) {
        Expert expertById = expertService.getReferenceById(expertId);
        return customerOrderService.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expertById);
    }

}
