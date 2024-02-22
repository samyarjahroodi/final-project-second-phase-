package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.Entity.user.Role;
import finalproject.finalproject.repository.*;
import finalproject.finalproject.service.dto.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
public class BaseTest {

    @Autowired
    protected DutyRepository dutyRepository;

    @Autowired
    protected CommentServiceImpl commentService;

    @Autowired
    protected DutyServiceImpl dutyService;

    @Autowired
    protected ExpertRepository expertRepository;

    @Autowired
    protected ExpertServiceImpl expertService;

    @Autowired
    protected CustomerOrderRepository customerOrderRepository;

    @Autowired
    protected SuggestionServiceImpl suggestionService;

    @Autowired
    protected SuggestionRepository suggestionRepository;

    @Autowired
    protected SubDutyRepository subDutyRepository;

    @Autowired
    protected SubDutyServiceImpl subDutyService;

    @Autowired
    protected AdminServiceImpl adminService;

    @Autowired
    protected CustomerRepository customerRepository;

    @Autowired
    protected CustomerServiceImpl customerService;

    @Autowired
    protected CustomerOrderServiceImpl customerOrderService;


    protected Expert createExpert() throws IOException {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\images.jpg";
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        ExpertDtoRequest expertDto = createExpertDto();
        Expert expert = Expert.builder()
                .firstname(expertDto.getFirstname())
                .lastname(expertDto.getLastname())
                .email(expertDto.getEmail())
                .password(expertDto.getPassword())
                .username(expertDto.getUsername())
                .image(expertService.setImageForExpert(imagePath))
                .role(Role.ROLE_EXPERT)
                .build();
        expert.setRegistrationStatus(RegistrationStatus.AWAITING_CONFIRMATION);
        expert.setWhenExpertRegistrationStatusChangedToAccepted(LocalDate.now());
        expertRepository.save(expert);
        return expert;
    }

    protected ExpertDtoRequest createExpertDto() throws IOException {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\images.jpg";
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        return ExpertDtoRequest.builder()
                .firstname("employee")
                .lastname("Employ")
                .email("employee@employee.com")
                .username("employee")
                .password("Abcd123!")
                .pathName(imagePath)
                .build();
    }

    protected Expert createAnotherExpert() throws IOException {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\images.jpg";
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        ExpertDtoRequest expertDto = createAnotherExpertDto();
        Expert expert = Expert.builder()
                .firstname(expertDto.getFirstname())
                .lastname(expertDto.getLastname())
                .email(expertDto.getEmail())
                .password(expertDto.getPassword())
                .username(expertDto.getUsername())
                .image(expertService.setImageForExpert(imagePath))
                .build();
        expert.setRegistrationStatus(RegistrationStatus.ACCEPTED);
        expert.setWhenExpertRegistrationStatusChangedToAccepted(LocalDate.now());
        expertRepository.save(expert);
        return expert;
    }

    protected ExpertDtoRequest createAnotherExpertDto() throws IOException {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\images.jpg";
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        return ExpertDtoRequest.builder()
                .firstname("another_employee")
                .lastname("Another Employ")
                .email("another_employee@employee.com")
                .username("another_employee")
                .password("Abcd123!")
                .build();
    }


    protected SuggestionDtoRequest createSuggestionDto(int suggestionPrice, LocalDate whenSuggestionCreated, LocalDate suggestedTimeToStartTheProject, int hoursThatTaken) {
        return SuggestionDtoRequest.builder()
                .suggestedPrice(suggestionPrice)
                .whenSuggestionCreated(whenSuggestionCreated)
                .suggestedTimeToStartTheProject(ZonedDateTime.from(suggestedTimeToStartTheProject))
                .hoursThatTaken(hoursThatTaken)
                .build();
    }

    protected Suggestion createSuggestion(int suggestionPrice, LocalDate suggestedTimeToStartTheProjectByExpert/*,int orderPrice,
                                          LocalDate suggestedTimeToStartTheProjectByCustomer,Duty duty, SubDuty subDuty*/) {
        SuggestionDtoRequest dto =
                createSuggestionDto(suggestionPrice, LocalDate.now(), suggestedTimeToStartTheProjectByExpert, 10);
        Suggestion suggestion = Suggestion.builder()
                .suggestedPrice(dto.getSuggestedPrice())
                .whenSuggestionCreated(dto.getWhenSuggestionCreated())
                .suggestedTimeToStartTheProject(dto.getSuggestedTimeToStartTheProject())
                .hoursThatTaken(dto.getHoursThatTaken())
                /*  .order(dto)*/
                .build();
        suggestionRepository.save(suggestion);
        return suggestion;
    }

    protected UserDtoRequest createUserDto() {
        return UserDtoRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .username("john_doe")
                .password("my_password@domain.com")
                .build();
    }

    protected CommentDtoRequest createCommentDtoRequest() {
        return CommentDtoRequest.builder()
                .comment("salam")
                .star(3.4)
                .build();
    }

    protected Customer createCustomer() {
        UserDtoRequest dto = createUserDto();
        Customer customer = Customer.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
        customerRepository.save(customer);
        return customer;
    }

    protected CustomerOrderDtoRequest createCustomerOrderDto(double orderPrice, LocalDate timeOfOrder
            , Status status, LocalDate suggestedTimeToStartTheProjectByCustomer, double subDutyPrice, Duty duty, SubDuty subDuty) {
        CustomerOrderDtoRequest customerOrderDto = CustomerOrderDtoRequest.builder()
                .description("maintaining walls")
                .price(orderPrice)
                .timeOfOrder(timeOfOrder)
                .status(status)
                .suggestedTimeToStartTheProjectByCustomer(suggestedTimeToStartTheProjectByCustomer)
                .duty(duty)
                .subDuty(subDuty)
                .build();
        return customerOrderDto;
    }

    protected CustomerOrder createCustomerOrder(int orderPrice, LocalDate timeOfOrder
            , Status status, LocalDate suggestedTimeToStartTheProjectByCustomer, double subDutyPrice, Duty duty, SubDuty subDuty) {
        CustomerOrderDtoRequest customerOrderDto = createCustomerOrderDto(orderPrice, timeOfOrder, status, suggestedTimeToStartTheProjectByCustomer, subDutyPrice, duty, subDuty);
        CustomerOrder customerOrder = CustomerOrder.builder()
                .description(customerOrderDto.getDescription())
                .price(customerOrderDto.getPrice())
                .timeOfOrder(customerOrderDto.getTimeOfOrder())
                .status(customerOrderDto.getStatus())
                .suggestedTimeToStartTheProjectByCustomer(customerOrderDto.getSuggestedTimeToStartTheProjectByCustomer())
                .duty(customerOrderDto.getDuty())
                .subDuty(customerOrderDto.getSubDuty())
                .build();
        customerOrderRepository.save(customerOrder);
        return customerOrder;
    }

    protected void addSubDutyToDutyByAdmin(Duty duty, SubDuty subDuty, int priceOfTheSubDuty) {
        adminService.addSubDutyToDutyByAdmin(createDuty(), Collections.singletonList(createSubDuty(priceOfTheSubDuty)));
    }

    protected DutyDtoRequest createDutyDto() {
        return DutyDtoRequest.builder()
                .name("home maintenance")
                .build();
    }

    protected Duty createDuty() {
        DutyDtoRequest dutyDto = createDutyDto();
        Duty duty = Duty.builder()
                .name(dutyDto.getName())
                .build();
        dutyRepository.save(duty);
        return duty;
    }

    protected SubDutyDtoRequest createSubDutyDto(int price) {
        return SubDutyDtoRequest.builder()
                .name("painting walls")
                .description("with white color")
                .price(price)
                .build();
    }

    protected SubDuty createSubDuty(int price) {
        SubDutyDtoRequest subDutyDto = createSubDutyDto(price);
        SubDuty subDuty = SubDuty.builder()
                .name(subDutyDto.getName())
                .description(subDutyDto.getDescription())
                .price(subDutyDto.getPrice())
                .build();
        subDutyRepository.save(subDuty);
        return subDuty;
    }

}