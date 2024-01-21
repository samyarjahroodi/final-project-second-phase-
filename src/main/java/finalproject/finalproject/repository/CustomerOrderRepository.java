package finalproject.finalproject.repository;


import finalproject.finalproject.model.operation.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Integer> {

}
