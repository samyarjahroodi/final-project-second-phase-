package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.repository.*;
import finalproject.finalproject.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
public class BaseTest {

    @Autowired
    protected DutyRepository dutyRepository;

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
        ExpertDto expertDto = createExpertDto();
        Expert expert = Expert.builder()
                .firstname(expertDto.getFirstname())
                .lastname(expertDto.getLastname())
                .email(expertDto.getEmail())
                .password(expertDto.getPassword())
                .username(expertDto.getUsername())
                .image(expertService.setImageForExpert(imagePath))
                .build();
        expert.setRegistrationStatus(RegistrationStatus.ACCEPTED);
        expert.setWhenExpertRegistered(LocalDate.now());
        expertRepository.save(expert);
        return expert;
    }

    protected ExpertDto createExpertDto() throws IOException {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\images.jpg";
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        return ExpertDto.builder()
                .firstname("employee")
                .lastname("Employ")
                .email("employee@employee.com")
                .username("employee")
                .password("my_password@domain.com")
                .profileImage(imageData)
                .build();
    }

    protected SuggestionDto createSuggestionDto(int suggestionPrice, LocalDate whenSuggestionCreated, LocalDate suggestedTimeToStartTheProject, int daysThatTaken) {
        return SuggestionDto.builder()
                .suggestedPrice(suggestionPrice)
                .whenSuggestionCreated(whenSuggestionCreated)
                .suggestedTimeToStartTheProject(suggestedTimeToStartTheProject)
                .daysThatTaken(daysThatTaken)
                .build();
    }

    protected Suggestion createSuggestion(int suggestionPrice,LocalDate suggestedTimeToStartTheProjectByExpert,int orderPrice,
                                          LocalDate suggestedTimeToStartTheProjectByCustomer,Duty duty, SubDuty subDuty) {
        SuggestionDto dto =
                createSuggestionDto(suggestionPrice, LocalDate.now(), suggestedTimeToStartTheProjectByExpert,10);
        Suggestion suggestion = Suggestion.builder()
                .suggestedPrice(dto.getSuggestedPrice())
                .whenSuggestionCreated(dto.getWhenSuggestionCreated())
                .suggestedTimeToStartTheProject(dto.getSuggestedTimeToStartTheProject())
                .daysThatTaken(dto.getDaysThatTaken())
                .order(createCustomerOrder(orderPrice, LocalDate.now(), Status.WAITING_EXPERT_SELECTION, suggestedTimeToStartTheProjectByCustomer, subDuty.getPrice(), duty, subDuty))
                .build();
        suggestionRepository.save(suggestion);
        return suggestion;
    }

    protected Customer createCustomer() {
        UserDto dto = UserDto.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .username("john_doe")
                .password("my_password@domain.com")
                .build();

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

    protected CustomerOrderDto createCustomerOrderDto(double orderPrice, LocalDate timeOfOrder
            , Status status, LocalDate suggestedTimeToStartTheProjectByCustomer, double subDutyPrice, Duty duty, SubDuty subDuty) {
        CustomerOrderDto customerOrderDto = CustomerOrderDto.builder()
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
        CustomerOrderDto customerOrderDto = createCustomerOrderDto(orderPrice, timeOfOrder, status, suggestedTimeToStartTheProjectByCustomer, subDutyPrice, duty, subDuty);
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
        adminService.addSubDutyToDutyByAdmin(createDuty(), createSubDuty(priceOfTheSubDuty));
    }


    protected Duty createDuty() {
        DutyDto dutyDto = DutyDto.builder()
                .name("home maintenance")
                .build();
        Duty duty = Duty.builder()
                .name(dutyDto.getName())
                .build();
        dutyRepository.save(duty);
        return duty;
    }

    protected SubDuty createSubDuty(int price) {
        SubDutyDto subDutyDto = SubDutyDto.builder()
                .name("painting walls")
                .description("with white color")
                .price(price)
                .build();
        SubDuty subDuty = SubDuty.builder()
                .name(subDutyDto.getName())
                .description(subDutyDto.getDescription())
                .price(subDutyDto.getPrice())
                .build();
        subDutyRepository.save(subDuty);
        return subDuty;
    }

}