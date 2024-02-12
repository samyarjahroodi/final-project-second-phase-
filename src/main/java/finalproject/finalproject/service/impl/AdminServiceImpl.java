package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.payment.Wallet;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.Entity.user.Role;
import finalproject.finalproject.exception.DuplicateException;
import finalproject.finalproject.exception.NotFoundException;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.*;
import finalproject.finalproject.service.AdminService;
import finalproject.finalproject.service.SubDutyService;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import finalproject.finalproject.service.validation.ValidateExpertDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static finalproject.finalproject.Entity.user.RegistrationStatus.AWAITING_CONFIRMATION;


@AllArgsConstructor
@Transactional
@Service
public class AdminServiceImpl
        implements AdminService {
    private final AdminRepository adminRepository;
    private final DutyServiceImpl dutyService;
    private final WalletServiceImpl walletService;
    private final ExpertServiceImpl expertService;
    private final SubDutyService subDutyService;
    private final SearchPersonServiceImpl searchPersonService;


    @Override
    public Duty createDuty(DutyDtoRequest dto) {
        validateDtoForCreateDuty(dto);

        if (dutyService.existsByName(dto.getName())) {
            throw new DuplicateException("This name exists in the database");
        }

        Duty duty = Duty.builder()
                .name(dto.getName())
                .build();
        return dutyService.save(duty);
    }

    private void validateDtoForCreateDuty(DutyDtoRequest dto) {
        if (dto == null) {
            throw new NullInputException("DTO cannot be null");
        }
        if (dto.getName() == null) {
            throw new NullInputException("DTO name cannot be null");
        }
    }


    @Override
    public SubDuty createSubDuty(SubDutyDtoRequest dto, Duty duty) {
        validateDtoForCreatingSubDuty(dto);
        validateDuty(duty);

        if (subDutyService.existsByName(dto.getName())) {
            throw new DuplicateException("This name exists in the database");
        }

        SubDuty subDuty = SubDuty.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .duty(duty)
                .price(dto.getPrice())
                .build();

        return subDutyService.save(subDuty);
    }

    private void validateDtoForCreatingSubDuty(SubDutyDtoRequest dto) {
        if (dto == null || dto.getPrice() == null || dto.getDescription() == null || dto.getName() == null) {
            throw new NullInputException("DTO fields cannot be null");
        }
    }

    private void validateDuty(Duty duty) {
        if (duty == null || duty.getId() == null) {
            throw new NullInputException("The given duty must not be null");
        }
        if (!dutyService.findById(duty.getId()).isPresent()) {
            throw new NullInputException("The duty with the given ID does not exist");
        }
    }


    @Override
    public void deleteDuty(Duty duty) {
        dutyService.delete(duty);
    }

    @Override
    public List<Duty> showDuties() {
        return dutyService.findAll();
    }

    @Override
    public List<SubDuty> showSubDuties() {
        return subDutyService.findAll();
    }

    @Override
    public void updateDetailsForSubDuty(SubDutyDtoRequest dto, SubDuty subDuty) {
        if (subDuty == null || dto == null || dto.getPrice() == null || dto.getDescription() == null || dto.getName() == null) {
            throw new NotFoundException("Sub duty or dto not found");
        }
        Optional<SubDuty> subDutyById = subDutyService.findById(subDuty.getId());
        if (subDutyById.isPresent()) {
            SubDuty existingSubDuty = subDutyById.get();

            existingSubDuty.setName(dto.getName());
            existingSubDuty.setDescription(dto.getDescription());
            existingSubDuty.setPrice(dto.getPrice());
            subDutyService.save(existingSubDuty);
        }
    }

    @Override
    public void createExpert(ExpertDtoRequest dto) throws IOException {
        ValidateExpertDto.validateExpertDtoRequest(dto);
        Wallet wallet = Wallet.builder()
                .creditOfWallet(0)
                .build();
        walletService.save(wallet);

        Expert expert = Expert.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .wallet(wallet)
                .role(Role.EXPERT)
                .registrationStatus(AWAITING_CONFIRMATION)
                .whenExpertRegistered(LocalDate.now())
                .image(expertService.setImageForExpert(dto.getPathName()))
                .fieldOfEndeavor(dto.getFieldOfEndeavor())
                .build();

        expertService.save(expert);
    }

    @Override
    public void addSubDutyToDutyByAdmin(Duty duty, List<SubDuty> subDuties) {
        if (duty == null || subDuties == null || subDuties.isEmpty()) {
            throw new NullInputException("Duty or sub-duties cannot be null or empty");
        }
        duty.setSubDuties(subDuties);
    }

    @Override
    public void deleteSubDutyFromTheExistDuty(SubDuty subDuty) {
        if (subDuty == null) {
            throw new NullInputException("sub duty cannot be null");
        }
        subDutyService.deleteSubDutyFromTheExistDuty(subDuty);
    }

    @Override
    public void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty) {
        if (subDuty == null || expert == null) {
            throw new NullInputException("Expert or sub duty cannot be null");
        }
        expertService.deleteSubDutyOFTheSpecificExpert(expert, subDuty);
    }

    @Override
    public void deleteExpert(Expert expert) {
        if (expert == null) {
            throw new NullInputException("Expert cannot be null");
        }
        expertService.delete(expert);
    }

    @Override
    public void addSubDutyToNewExpert(Expert expert, SubDuty subDuty) {
        if (subDuty == null || expert == null) {
            throw new NullInputException("Expert or sub duty cannot be null or empty");
        }
        if (subDuty.getExperts() == null) {
            List<Expert> experts = new ArrayList<>();
            experts.add(expert);
            subDuty.setExperts(experts);
            changeTheStatusOfExpert(expert);
        } else {
            List<Expert> experts1 = subDuty.getExperts();
            experts1.add(expert);
            changeTheStatusOfExpert(expert);
        }
        subDutyService.save(subDuty);
    }

    @Override
    public void changeTheStatusOfExpert(Expert expert) {
        if (expert == null || expertService.findById(expert.getId()).isEmpty()) {
            throw new NullInputException("Expert object cannot be null");
        }
        expertService.updateRegistrationStatusForSpecificExpert(expert);
    }

    @Override
    public List<Person> search(SearchForPerson search) {
        return searchPersonService.search(search);
    }
}
