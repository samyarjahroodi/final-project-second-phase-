package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.mapper.DutyMapper;
import finalproject.finalproject.mapper.SuggestionMapper;
import finalproject.finalproject.repository.CustomerOrderRepository;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import finalproject.finalproject.service.dto.response.DutyDtoResponse;
import finalproject.finalproject.service.dto.response.SuggestionDtoResponse;
import finalproject.finalproject.service.impl.SuggestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {
    private final ExpertRepository expertRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final SuggestionServiceImpl suggestionService;
    private final ModelMapper modelMapper;

    @PostMapping("/create-Suggestion-For-Expert/{expertId}/{customerOrderId}")
    public ResponseEntity<SuggestionDtoResponse> createSuggestionForExpert(@PathVariable Integer expertId
            , @RequestBody SuggestionDtoRequest dto, @PathVariable Integer customerOrderId) throws Exception {
        Suggestion suggestion = SuggestionMapper.INSTANCE.requestDtoToModel(dto);
        Expert expertById = expertRepository.getReferenceById(expertId);
        CustomerOrder customerOrderById = customerOrderRepository.getReferenceById(customerOrderId);
        suggestionService.createSuggestionForExpert(expertById, dto, customerOrderById);
        SuggestionDtoResponse suggestionDtoResponse = modelMapper.map(suggestion, SuggestionDtoResponse.class);
        return new ResponseEntity<>(suggestionDtoResponse, HttpStatus.CREATED);
    }
}
