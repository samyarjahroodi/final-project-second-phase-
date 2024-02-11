package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.impl.CustomerOrderServiceImpl;
import finalproject.finalproject.service.impl.ExpertServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {
    private final ExpertServiceImpl expertService;
    private final CustomerOrderServiceImpl customerOrderService;

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

}
