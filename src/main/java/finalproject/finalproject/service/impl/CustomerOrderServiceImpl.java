package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.payment.Wallet;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.exception.PriceException;
import finalproject.finalproject.exception.TimeException;
import finalproject.finalproject.repository.CustomerOrderRepository;
import finalproject.finalproject.service.CustomerOrderService;
import finalproject.finalproject.service.dto.request.CustomerOrderDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServiceImpl
        implements CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerServiceImpl customerService;
    private final WalletServiceImpl walletService;
    private final ExpertServiceImpl expertService;

    @Autowired
    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, @Lazy CustomerServiceImpl customerService, WalletServiceImpl walletService, ExpertServiceImpl expertService) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerService = customerService;
        this.walletService = walletService;
        this.expertService = expertService;
    }

    @Override
    public CustomerOrder publishOrder(Customer customer, CustomerOrderDtoRequest dto, Duty duty, SubDuty subDuty) {
        if (customer == null || duty == null || subDuty == null || dto == null) {
            throw new NullInputException("customer , duty , subDuty or dto cannot be null");
        }
        validatePrice(subDuty, dto);
        validateExpectedTime(dto);

        CustomerOrder customerOrder = CustomerOrder.builder()
                .description(dto.getDescription())
                .address(dto.getAddress())
                .duty(duty)
                .subDuty(subDuty)
                .suggestedTimeToStartTheProjectByCustomer(dto.getSuggestedTimeToStartTheProjectByCustomer())
                .status(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS)
                .customer(customer)
                .price(dto.getPrice())
                .timeOfOrder(dto.getTimeOfOrder())
                .build();
        customerService.save(customer);
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    @Transactional
    public void reduceStarsOfExpertIfNeeded(CustomerOrder customerOrder) {
        Suggestion suggestion = findSuggestionThatIsApproved(customerOrder);
        Expert expert = suggestion.getExpert();
        ZonedDateTime suggestedTimeToStartTheProject = suggestion.getSuggestedTimeToStartTheProject();
        ZonedDateTime timeThatStatusChangedToFinished = customerOrder.getTimeThatStatusChangedToFinished();
        int hour = timeThatStatusChangedToFinished.getHour();
        int hour1 = suggestedTimeToStartTheProject.getHour();
        if (hour - hour1 > suggestion.getHoursThatTaken()) {
            int hoursDifference = hour - hour1;
            for (int i = 0; i < hoursDifference; i++) {
                double star = expert.getStar().intValue();
                if (star < 0) {
                    Wallet wallet = expert.getWallet();
                    wallet.setActive(false);
                    walletService.save(wallet);
                    expertService.save(expert);
                    break;
                }
                expert.setStar(star - i);
                expertService.save(expert);
            }
        }
    }


    public Suggestion findSuggestionThatIsApproved(CustomerOrder customerOrder) {
        return customerOrder.getSuggestions().stream()
                .filter(Suggestion::getIsApproved)
                .findFirst()
                .orElse(null);
    }

    public void validatePrice(SubDuty subDuty, CustomerOrderDtoRequest dto) {
        if (subDuty.getPrice() > dto.getPrice()) {
            throw new PriceException("Your price is not enough");
        }
    }

    public void validateExpectedTime(CustomerOrderDtoRequest dto) {
        if (dto.getTimeOfOrder().isAfter(dto.getSuggestedTimeToStartTheProjectByCustomer())) {
            throw new TimeException("Your expected time to start is before the current time");
        }
    }

    @Override
    public List<CustomerOrder> showCustomerOrdersToExpertBasedOnCustomerOrderStatus(Expert expert) {
        if (expert == null) {
            throw new NullInputException("Expert cannot be null");
        }
        return customerOrderRepository.showCustomerOrdersToExpertBasedOnCustomerOrderStatus(expert);
    }

    @Override
    public List<Suggestion> showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(Customer customer) {
        if (customer == null) {
            throw new NullInputException("Customer cannot be null");
        }
        return customerOrderRepository.showCustomerOrderOfSpecificCustomerBasedOnPriceOfSuggestions(customer);
    }

    @Override
    public List<Suggestion> showSuggestionsBasedOnStarOfExpert(Customer customer) {
        if (customer == null) {
            throw new NullInputException("Customer cannot be null");
        }
        return customerOrderRepository.showSuggestionsBasedOnStarOfExpert(customer);
    }

    @Override
    @Transactional
    public CustomerOrder save(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public Optional<CustomerOrder> findById(Integer integer) {
        return customerOrderRepository.findById(integer);
    }

    @Override
    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    @Override
    public List<CustomerOrder> findAllById(Iterable<Integer> integers) {
        return customerOrderRepository.findAllById(integers);
    }

    @Override
    public List<CustomerOrder> findAll(Sort sort) {
        return customerOrderRepository.findAll(sort);
    }

    @Override
    public Page<CustomerOrder> findAll(Pageable pageable) {
        return customerOrderRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        customerOrderRepository.deleteById(integer);
    }

    @Override
    public void delete(CustomerOrder entity) {
        customerOrderRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends CustomerOrder> entities) {
        customerOrderRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        customerOrderRepository.deleteAll();
    }

    @Override
    public CustomerOrder getReferenceById(Integer integer) {
        return customerOrderRepository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }


}