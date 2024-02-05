package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.Wallet;
import finalproject.finalproject.repository.*;
import finalproject.finalproject.service.AdminService;
import finalproject.finalproject.service.SubDutyService;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import lombok.AllArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
    private final DutyRepository dutyRepository;
    private final SubDutyRepository subDutyRepository;
    private final ExpertRepository expertRepository;
    private final WalletRepository walletRepository;
    private final ExpertServiceImpl expertService;
    private final AdminRepository adminRepository;
    private final SubDutyService subDutyService;


    public Duty createDuty(DutyDtoRequest dto) {
        if (dto == null) {
            throw new NullPointerException("dto cannot be null");
        }
        String dtoName = dto.getName();
        if (dutyRepository.findAll().stream().anyMatch(d -> d.getName().equals(dtoName))) {
            throw new IllegalArgumentException("You already have this duty");
        }
        Duty duty = Duty.builder()
                .name(dto.getName())
                .build();
        return dutyRepository.save(duty);
    }

    public SubDuty createSubDuty(SubDutyDtoRequest dto, Duty duty) {
        if (dto == null ) {
            throw new NullPointerException("dto cannot be null");
        }
        Optional<Duty> dutyById = dutyRepository.findById(duty.getId());
        if (dutyById.isPresent()) {
            SubDuty subDuty = SubDuty.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .duty(duty)
                    .price(dto.getPrice())
                    .build();
            if (subDutyRepository.findAll().stream().anyMatch(s -> s.getName().equals(subDuty.getName()))) {
                throw new IllegalArgumentException("you already have this sub duty");
            }
            return subDutyRepository.save(subDuty);
        } else {
            throw new InvalidDataAccessApiUsageException("The given id must not be null");
        }
    }

    public void deleteDuty(Duty duty) {
        dutyRepository.delete(duty);
    }

    public List<Duty> showDuties() {
        return dutyRepository.findAll();
    }

    public List<SubDuty> showSubDuties() {
        return subDutyRepository.findAll();
    }

    public void updateDetailsForSubDuty(SubDutyDtoRequest dto, SubDuty subDuty) {
        if (subDuty == null || dto == null) {
            throw new NoSuchElementException("Sub duty or dto not found");
        }
        Optional<SubDuty> subDutyById = subDutyRepository.findById(subDuty.getId());
        if (subDutyById.isPresent()) {
            SubDuty existingSubDuty = subDutyById.get();

            existingSubDuty.setName(dto.getName());
            existingSubDuty.setDescription(dto.getDescription());
            existingSubDuty.setPrice(dto.getPrice());
            subDutyRepository.save(existingSubDuty);
        }
    }


    public void createExpert(ExpertDtoRequest dto) throws IOException {
        if (dto == null) {
            throw new IllegalArgumentException("dto cannot be null");
        }
        Wallet wallet = Wallet.builder()
                .creditOfWallet(0)
                .build();
        walletRepository.save(wallet);

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

        expertRepository.save(expert);
    }


    public void addSubDutyToDutyByAdmin(Duty duty, List<SubDuty> subDuties) {
        if (duty == null || subDuties == null || subDuties.isEmpty()) {
            throw new IllegalArgumentException("Duty or sub-duties cannot be null or empty");
        }
        duty.setSubDuties(subDuties);
    }


    public void deleteSubDutyFromTheExistDuty(SubDuty subDuty) {
        if (subDuty == null) {
            throw new IllegalArgumentException("sub duty cannot be null");
        }
        subDutyRepository.deleteSubDutyFromTheExistDuty(subDuty);
    }

    public void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty) {
        if (subDuty == null || expert == null) {
            throw new IllegalArgumentException("Expert or sub duty cannot be null");
        }
        expertService.deleteSubDutyOFTheSpecificExpert(expert, subDuty);
    }

    public void deleteExpert(Expert expert) {
        if (expert == null) {
            throw new IllegalArgumentException("Expert cannot be null");
        }
        expertRepository.delete(expert);
    }


    public void addSubDutyToNewExpert(Expert expert, SubDuty subDuty) {
        if (subDuty == null || expert == null) {
            throw new IllegalArgumentException("Expert or sub duty cannot be null or empty");
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
        subDutyRepository.save(subDuty);
    }

    public void changeTheStatusOfExpert(Expert expert) {
        if (expert == null) {
            throw new IllegalArgumentException("Expert object cannot be null");
        }
        expertService.updateRegistrationStatusForSpecificExpert(expert);
    }
}
