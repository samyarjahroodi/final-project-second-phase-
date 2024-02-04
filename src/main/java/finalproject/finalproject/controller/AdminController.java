package finalproject.finalproject.controller;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.mapper.DutyMapper;
import finalproject.finalproject.mapper.ExpertMapper;
import finalproject.finalproject.mapper.SubDutyMapper;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import finalproject.finalproject.service.dto.response.DutyDtoResponse;
import finalproject.finalproject.service.dto.response.ExpertDtoResponse;
import finalproject.finalproject.service.dto.response.SubDutyDtoResponse;
import finalproject.finalproject.service.impl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final DutyRepository dutyRepository;
    private final ModelMapper modelMapper;
    private final SubDutyRepository subDutyRepository;
    private final ExpertRepository expertRepository;


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
        Duty referenceById = dutyRepository.getReferenceById(id);
        adminService.createSubDuty(dto, referenceById);
        SubDutyDtoResponse subDutyDtoResponse = modelMapper.map(subDuty, SubDutyDtoResponse.class);
        return new ResponseEntity<>(subDutyDtoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-duty/{id}")
    public void deleteDuty(@PathVariable Integer id) {
        Duty referenceById = dutyRepository.getReferenceById(id);
        adminService.deleteDuty(referenceById);
    }

    @GetMapping("/duties")
    public List<Duty> showDuties() {
        return adminService.showDuties();
    }

    @GetMapping("/sub-duties")
    public List<SubDuty> showSubDuties() {
        return adminService.showSubDuties();
    }

    @PutMapping("/updateDetailsForSubDuty/{id}")
    public ResponseEntity<SubDutyDtoResponse> updateDetailsForSubDuty(@RequestBody SubDutyDtoRequest dto, @PathVariable Integer id) {
        SubDuty subDuty = SubDutyMapper.INSTANCE.requestDtoToModel(dto);
        SubDuty referenceById = subDutyRepository.getReferenceById(id);
        adminService.updateDetailsForSubDuty(dto, referenceById);
        SubDutyDtoResponse subDutyDtoResponse = modelMapper.map(subDuty, SubDutyDtoResponse.class);
        return new ResponseEntity<>(subDutyDtoResponse, HttpStatus.OK);
    }

    @PostMapping("/createExpert")
    public ResponseEntity<ExpertDtoResponse> createExpert(@RequestBody ExpertDtoRequest dto) throws IOException {
        Expert expert = ExpertMapper.INSTANCE.requestDtoToModel(dto);
        adminService.createExpert(dto);
        ExpertDtoResponse expertDtoResponse = modelMapper.map(expert, ExpertDtoResponse.class);
        return new ResponseEntity<>(expertDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/add-Sub-Duty-To-New-Expert/{expertId}/{subDutyId}")
    public ResponseEntity<String> addSubDutyToNewExpert(@PathVariable Integer expertId, @PathVariable Integer subDutyId) {
        Expert expertById = expertRepository.getReferenceById(expertId);
        SubDuty subDutyById = subDutyRepository.getReferenceById(subDutyId);
        adminService.addSubDutyToNewExpert(expertById, subDutyById);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-Sub-Duty-From-The-Exist-Duty/{id}")
    public ResponseEntity<String> deleteSubDutyFromTheExistDuty(@PathVariable Integer id) {
        SubDuty subDutyById = subDutyRepository.getReferenceById(id);
        adminService.deleteSubDutyFromTheExistDuty(subDutyById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-Sub-Duty-OF-The-Specific-Expert/{subDutyId}/{expertId}")
    public ResponseEntity<String> deleteSubDutyOFTheSpecificExpert(@PathVariable Integer subDutyId, @PathVariable Integer expertId) {
        SubDuty subDutyById = subDutyRepository.getReferenceById(subDutyId);
        Expert expertById = expertRepository.getReferenceById(expertId);
        adminService.deleteSubDutyOFTheSpecificExpert(expertById, subDutyById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-Expert/{expertId}")
    public ResponseEntity<String> deleteExpert(@PathVariable Integer expertId) {
        Expert expertById = expertRepository.getReferenceById(expertId);
        adminService.deleteExpert(expertById);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/change-The-Status-Of-Expert/{expertId}")
    public ResponseEntity<String> changeTheStatusOfExpert(@PathVariable Integer expertId) {
        Expert expertById = expertRepository.getReferenceById(expertId);
        adminService.changeTheStatusOfExpert(expertById);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
