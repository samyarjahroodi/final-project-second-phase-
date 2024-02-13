package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.mapper.DutyMapper;
import finalproject.finalproject.mapper.ExpertMapper;
import finalproject.finalproject.mapper.SubDutyMapper;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import finalproject.finalproject.service.dto.response.DutyDtoResponse;
import finalproject.finalproject.service.dto.response.ExpertDtoResponse;
import finalproject.finalproject.service.dto.response.SubDutyDtoResponse;
import finalproject.finalproject.service.impl.AdminServiceImpl;
import finalproject.finalproject.service.impl.DutyServiceImpl;
import finalproject.finalproject.service.impl.ExpertServiceImpl;
import finalproject.finalproject.service.impl.SubDutyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final ExpertServiceImpl expertService;
    private final DutyServiceImpl dutyService;
    private final ModelMapper modelMapper;
    private final SubDutyServiceImpl subDutyService;


    @PostMapping("/create-duty")
    public ResponseEntity<DutyDtoResponse> createDuty(@RequestBody DutyDtoRequest dto) {
        DutyMapper.INSTANCE.requestDtoToModel(dto);
        Duty duty = adminService.createDuty(dto);
        DutyDtoResponse dutyDtoResponse = modelMapper.map(duty, DutyDtoResponse.class);
        return new ResponseEntity<>(dutyDtoResponse, HttpStatus.CREATED);
    }

    @PostMapping("/create-sub-duty/{id}")
    public ResponseEntity<SubDutyDtoResponse> createSubDuty(@RequestBody SubDutyDtoRequest dto, @PathVariable Integer id) {
        SubDuty subDuty = SubDutyMapper.INSTANCE.requestDtoToModel(dto);
        Duty referenceById = dutyService.getReferenceById(id);
        adminService.createSubDuty(dto, referenceById);
        SubDutyDtoResponse subDutyDtoResponse = modelMapper.map(subDuty, SubDutyDtoResponse.class);
        return new ResponseEntity<>(subDutyDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-duty/{id}")
    public void deleteDuty(@PathVariable Integer id) {
        Duty referenceById = dutyService.getReferenceById(id);
        adminService.deleteDuty(referenceById);
    }

    @GetMapping("/duties")
    public List<Duty> showDuties() {
        return adminService.showDuties();
    }

    @GetMapping("/experts")
    public List<Expert> showExpert() {
        return expertService.showExpert();
    }

    @GetMapping("/sub-duties")
    public List<SubDuty> showSubDuties() {
        return adminService.showSubDuties();
    }

    @PutMapping("/updateDetailsForSubDuty/{id}")
    public ResponseEntity<SubDutyDtoResponse> updateDetailsForSubDuty(@RequestBody SubDutyDtoRequest dto, @PathVariable Integer id) {
        SubDuty subDuty = SubDutyMapper.INSTANCE.requestDtoToModel(dto);
        SubDuty referenceById = subDutyService.getReferenceById(id);
        adminService.updateDetailsForSubDuty(dto, referenceById);
        SubDutyDtoResponse subDutyDtoResponse = modelMapper.map(subDuty, SubDutyDtoResponse.class);
        return new ResponseEntity<>(subDutyDtoResponse, HttpStatus.OK);
    }


    @PutMapping("/add-Sub-Duty-To-New-Expert/{expertId}/{subDutyId}")
    public ResponseEntity<String> addSubDutyToNewExpert(@PathVariable Integer expertId, @PathVariable Integer subDutyId) {
        Expert expertById = expertService.getReferenceById(expertId);
        SubDuty subDutyById = subDutyService.getReferenceById(subDutyId);
        adminService.addSubDutyToNewExpert(expertById, subDutyById);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-Sub-Duty-From-The-Exist-Duty/{id}")
    public ResponseEntity<String> deleteSubDutyFromTheExistDuty(@PathVariable Integer id) {
        SubDuty subDutyById = subDutyService.getReferenceById(id);
        adminService.deleteSubDutyFromTheExistDuty(subDutyById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-Sub-Duty-OF-The-Specific-Expert/{subDutyId}/{expertId}")
    public ResponseEntity<String> deleteSubDutyOFTheSpecificExpert(@PathVariable Integer subDutyId, @PathVariable Integer expertId) {
        SubDuty subDutyById = subDutyService.getReferenceById(subDutyId);
        Expert expertById = expertService.getReferenceById(expertId);
        adminService.deleteSubDutyOFTheSpecificExpert(expertById, subDutyById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-The-Status-Of-Expert/{expertId}")
    public ResponseEntity<String> changeTheStatusOfExpert(@PathVariable Integer expertId) {
        Expert expertById = expertService.getReferenceById(expertId);
        adminService.changeTheStatusOfExpert(expertById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-Expert/{expertId}")
    public ResponseEntity<String> deleteExpert(@PathVariable Integer expertId) {
        Expert expertById = expertService.getReferenceById(expertId);
        adminService.deleteExpert(expertById);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/search")
    public List<Person> search(@RequestBody SearchForPerson search) {
        return adminService.search(search);
    }


    @GetMapping("/show-Sub-Duties-Of-Specific-Duty/{dutyId}")
    public List<SubDuty> showSubDutiesOfSpecificDuty(@PathVariable Integer dutyId) {
        Duty dutyById = dutyService.getReferenceById(dutyId);
        return dutyService.showSubDutiesOfSpecificDuty(dutyById);
    }

}
