package finalproject.finalproject.service.impl;


import base.service.Impl.BaseEntityServiceImpl;
import entity.operation.CustomerOrder;
import repository.CustomerOrderRepository;
import service.CustomerOrderService;

public class CustomerOrderServiceImpl
        extends BaseEntityServiceImpl<CustomerOrder, Integer, CustomerOrderRepository>
        implements CustomerOrderService {

    public CustomerOrderServiceImpl(CustomerOrderRepository repository) {
        super(repository);
    }
}