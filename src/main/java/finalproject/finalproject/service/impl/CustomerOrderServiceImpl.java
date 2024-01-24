package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.repository.CustomerOrderRepository;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.service.CustomerOrderService;
import finalproject.finalproject.service.dto.CustomerOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl
        implements CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final DutyRepository dutyRepository;

    public void publishOrder(Customer customer, CustomerOrderDto dto, Duty duty, SubDuty subDuty) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDescription(dto.getDescription());
        customerOrder.setAddress(dto.getAddress());
        System.out.println(dutyRepository.findAll());
        customerOrder.setDuty(duty);
        if (subDuty.getPrice() > dto.getPrice()) {
            throw new IllegalArgumentException("your price is not enough");
        } else {
            subDuty.setPrice(dto.getPrice());
        }
        customerOrder.setSubDuty(subDuty);
        LocalDate now = LocalDate.now();
        customerOrder.setTimeOfOrder(now);
        LocalDate exceptedTimeToStart = dto.getTimeOfOrder();
        if (compareStartedTimeAndTimeOfOrder(now, exceptedTimeToStart)) {
            customerOrder.setStarOfTheProject(exceptedTimeToStart);
        } else {
            System.exit(0);
        }
        customerOrder.setStatus(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);
        customerOrder.setCustomer(customer);
        customerOrderRepository.save(customerOrder);
    }

    private static Boolean compareStartedTimeAndTimeOfOrder(LocalDate timeOfOrder, LocalDate exceptedTimeToStart) {
        if (timeOfOrder.isAfter(exceptedTimeToStart)) {
            throw new IllegalArgumentException("your time for starting a project is before the time that your order registered!!");
        } else {
            return true;
        }
    }
}