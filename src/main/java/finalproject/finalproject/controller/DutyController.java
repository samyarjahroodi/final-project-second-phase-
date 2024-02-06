package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.service.impl.DutyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/duty")
public class DutyController {
    private final DutyServiceImpl dutyService;

    @GetMapping("/show-Sub-Duties-Of-Specific-Duty/{dutyId}")
    public List<SubDuty> showSubDutiesOfSpecificDuty(@PathVariable Integer dutyId) {
        Duty dutyById = dutyService.getReferenceById(dutyId);
        return dutyService.showSubDutiesOfSpecificDuty(dutyById);
    }
}
