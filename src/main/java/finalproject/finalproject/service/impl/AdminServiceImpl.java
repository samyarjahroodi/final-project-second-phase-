package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.Entity.utility.Wallet;
import finalproject.finalproject.repository.DutyRepository;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.AdminService;
import finalproject.finalproject.service.SubDutyService;
import finalproject.finalproject.service.dto.DutyDto;
import finalproject.finalproject.service.dto.ExpertDto;
import finalproject.finalproject.service.dto.SubDutyDto;
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
    private final DutyRepository dutyRepository;
    private final SubDutyRepository subDutyRepository;
    private final ExpertRepository expertRepository;
    private final WalletRepository walletRepository;
    private final ExpertServiceImpl expertService;
    private final SubDutyService subDutyService;


    public void createDuty(DutyDto dto) {
        String dtoName = dto.getName();
        if (dutyRepository.findAll().stream().anyMatch(d -> d.getName().equals(dtoName))) {
            throw new IllegalArgumentException("You already have this duty");
        }
        Duty duty = Duty.builder()
                .name(dto.getName())
                .build();
        dutyRepository.save(duty);
    }

    public void createSubDuty(SubDutyDto dto, Duty duty) {
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
            subDutyRepository.save(subDuty);
        } else {
            throw new NullPointerException("duty not found");
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

    public void updateDetailsForSubDuty(SubDutyDto dto, SubDuty subDuty) {
        Optional<SubDuty> subDutyById = subDutyRepository.findById(subDuty.getId());
        if (subDutyById.isPresent()) {
            SubDuty existingSubDuty = subDutyById.get();

            existingSubDuty.setName(dto.getName());
            existingSubDuty.setDescription(dto.getDescription());
            existingSubDuty.setPrice(dto.getPrice());
            subDutyRepository.save(existingSubDuty);
        } else {
            throw new NoSuchElementException("Sub duty not found");
        }
    }


    public void createExpert(ExpertDto dto) throws IOException {
        Wallet wallet = Wallet.builder()
                .creditOfWallet(0)
                .build();

        walletRepository.save(wallet);
        Expert expert = new Expert();
        expert.setFirstname(dto.getFirstname());
        expert.setLastname(dto.getLastname());
        expert.setEmail(dto.getEmail());
        expert.setPassword(dto.getPassword());
        expert.setUsername(dto.getUsername());
        expert.setWallet(wallet);
        expert.setRegistrationStatus(AWAITING_CONFIRMATION);
        expert.setStar(expertRepository.averageStarOfExpert(expert));
        expert.setWhenExpertRegistered(LocalDate.now());
        expert.setImage(expertService.setImageForExpert("C:\\Users\\Samyar\\Desktop\\images.jpg"));
        expertRepository.save(expert);
    }

    public void addSubDutyToDutyByAdmin(Duty duty, List<SubDuty> subDuties) {
        duty.setSubDuties(subDuties);
    }

    public void deleteSubDutyFromTheExistDuty(SubDuty subDuty) {
        subDutyRepository.deleteSubDutyFromTheExistDuty(subDuty);
    }

    public void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty) {
        expertService.deleteSubDutyOFTheSpecificExpert(expert, subDuty);
    }

    public void deleteExpert(Expert expert) {
        expertRepository.delete(expert);
    }


    public void addSubDutyToNewExpert(Expert expert, SubDuty subDuty) {
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

    private void changeTheStatusOfExpert(Expert expert) {
        expertRepository.updateRegistrationStatusForSpecificExpert(RegistrationStatus.ACCEPTED, expert);
    }
}
