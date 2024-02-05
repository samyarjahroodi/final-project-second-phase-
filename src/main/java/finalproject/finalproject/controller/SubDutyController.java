package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.impl.SubDutyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sub-duty")
public class SubDutyController {
    private final SubDutyRepository subDutyRepository;
    private final SubDutyServiceImpl subDutyService;

    @DeleteMapping("/show-Sub-Duties-OfSpecificDuty/{subDutyId}")
    public void showSubDutiesOfSpecificDuty(@PathVariable Integer subDutyId){
        SubDuty subDutyById = subDutyRepository.getReferenceById(subDutyId);
        subDutyService.deleteSubDutyFromTheExistDuty(subDutyById);
    }
}
