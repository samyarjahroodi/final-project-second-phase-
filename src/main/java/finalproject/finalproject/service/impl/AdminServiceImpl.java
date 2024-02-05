package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.Wallet;
import finalproject.finalproject.exception.DuplicateException;
import finalproject.finalproject.exception.NotFoundException;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.*;
import finalproject.finalproject.service.AdminService;
import finalproject.finalproject.service.SubDutyService;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
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

    @Override
    public Duty createDuty(DutyDtoRequest dto) {
        if (dto == null) {
            throw new NullInputException("dto cannot be null");
        }
        String dtoName = dto.getName();
        if (dutyService.findAll().stream().anyMatch(d -> d.getName().equals(dtoName))) {
            throw new DuplicateException("You already have this duty");
        }
        Duty duty = Duty.builder()
                .name(dto.getName())
                .build();
        return dutyService.save(duty);
    }

    @Override
    public SubDuty createSubDuty(SubDutyDtoRequest dto, Duty duty) {
        if (dto == null) {
            throw new NullInputException("dto cannot be null");
        }
        Optional<Duty> dutyById = dutyService.findById(duty.getId());
        if (dutyById.isPresent()) {
            SubDuty subDuty = SubDuty.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .duty(duty)
                    .price(dto.getPrice())
                    .build();
            if (subDutyService.findAll().stream().anyMatch(s -> s.getName().equals(subDuty.getName()))) {
                throw new DuplicateException("you already have this sub duty");
            }
            return subDutyService.save(subDuty);
        } else {
            throw new NullInputException("The given id must not be null");
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
        if (subDuty == null || dto == null) {
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
        if (dto == null) {
            throw new NullInputException("dto cannot be null");
        }
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
                .registrationStatus(AWAITING_CONFIRMATION)
                .whenExpertRegistered(LocalDate.now())
                .image(expertService.setImageForExpert(dto.getPathName()))
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
}
