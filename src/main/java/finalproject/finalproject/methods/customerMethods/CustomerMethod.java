package finalproject.finalproject.methods.customerMethods;


import entity.duty.Duty;
import entity.duty.SubDuty;
import entity.operation.CustomerOrder;
import entity.operation.Status;
import entity.user.Customer;
import entity.user.Role;
import entity.utility.Wallet;
import methods.SameMethods;
import service.impl.*;
import utility.ApplicationContext;
import utility.SecurityContext;
import validation.Validation;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;


public class CustomerMethod extends SameMethods {
    static CustomerServiceImpl customerService = ApplicationContext.getCustomerService();
    static WalletServiceImpl walletService = ApplicationContext.getWalletService();
    static DutyServiceImpl dutyService = ApplicationContext.getDutyService();
    static SubDutyServiceImpl subDutyService = ApplicationContext.getSubDutyService();
    static CustomerOrderServiceImpl customerOrderService = ApplicationContext.getCustomerOrderService();

    static Customer customer = new Customer();
    static Scanner scanner = new Scanner(System.in);


    public static void createCustomer() {
        customer.setFirstname("samyar");
        customer.setLastname("jahroodi");
        customer.setEmail(setEmail("samyar@gmail.com"));
        customer.setPassword(setPassword("123bcd"));
        customer.setUsername("samyar1");
        Wallet wallet = new Wallet();
        wallet.setCreditOfWallet(200);
        walletService.saveOrUpdate(wallet);
        customer.setWallet(wallet);
        customer.setRole(Role.CUSTOMER);
        customerService.saveOrUpdate(customer);
        SecurityContext.fillContext(customer);
    }

    public static void changePassword(String username, String oldPassword, String newPassword) {
        for (Customer c : customerService.findAll()) {
            if (username.equals(c.getUsername())) {
                for (Customer c1 : customerService.findAll()) {
                    if (c1.getPassword().equals(oldPassword)) {
                        Customer customer = customerService.getId(username, oldPassword);
                        if (Validation.isValidPassword(newPassword)) {
                            customer.setPassword(newPassword);
                            customerService.saveOrUpdate(customer);
                            return;
                        } else {
                            System.out.println("your password is not strong");
                        }
                    }
                }
            }
        }
        throw new NullPointerException("null or empty username");
    }

    public static void publishOrder(Integer customerId) {
        CustomerOrder customerOrder = new CustomerOrder();
        Optional<Customer> customerById = customerService.findById(customerId);
        if (customerById.isPresent()) {
            Customer customer = customerById.get();
            customerOrder.setDescription("Im building a house");
            customerOrder.setAddress("Farmanie street");

            try {
                System.out.println("which duty : ");
                showDuties();
                int i = scanner.nextInt();
                Optional<Duty> dutyById = dutyService.findById(i);
                Duty duty = dutyById.get();
                customerOrder.setDuty(duty);
                System.out.println("which sub duty : ");
                System.out.println(dutyService.showSubDutiesOfSpecificDuty(i));
                int i1 = scanner.nextInt();
                Optional<SubDuty> subDutyById = subDutyService.findById(i1);
                SubDuty subDuty = subDutyById.get();
                int price = 5000;
                if (subDuty.getPrice() > price) {
                    System.out.println("your price is not enough");
                    System.exit(0);
                } else {
                    subDuty.setPrice(price);
                }
                customerOrder.setSubDuty(subDuty);
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
            LocalDate now = LocalDate.now();
            customerOrder.setTimeOfOrder(now);
            LocalDate exceptedTimeToStart = LocalDate.of(2024, 1, 10);
            if (compareStartedTimeAndTimeOfOrder(now, exceptedTimeToStart)) {
                customerOrder.setStarOfTheProject(exceptedTimeToStart);
            } else {
                System.exit(0);
            }
            customerOrder.setStatus(Status.WAITING_FOR_THE_SUGGESTION_OF_EXPERTS);
            customerOrder.setCustomer(customer);
            customerOrderService.saveOrUpdate(customerOrder);

        } else {
            System.out.println("customer is not found ");
        }

    }

    private static Boolean compareStartedTimeAndTimeOfOrder(LocalDate timeOfOrder, LocalDate exceptedTimeToStart) {
        if (timeOfOrder.isAfter(exceptedTimeToStart)) {
            System.out.println("your time for starting a project is before the time that your order registered!!");
            return false;
        } else {
            return true;
        }
    }

}
