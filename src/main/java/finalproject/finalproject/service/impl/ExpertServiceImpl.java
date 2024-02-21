package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.payment.Wallet;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.Entity.user.Role;
import finalproject.finalproject.exception.NotValidSizeException;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.service.ExpertService;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static finalproject.finalproject.Entity.user.RegistrationStatus.NEW;
import static finalproject.finalproject.service.validation.ValidateUserDto.validateExpertDtoRequest;


@Service
@Primary
@Transactional
public class ExpertServiceImpl
        extends PersonServiceImpl<Expert, ExpertRepository>
        implements ExpertService {

    private final WalletServiceImpl walletService;
    private final SubDutyServiceImpl subDutyService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ExpertServiceImpl(ExpertRepository repository, JavaMailSender mailSender, WalletServiceImpl walletService, SubDutyServiceImpl subDutyService, BCryptPasswordEncoder passwordEncoder) {
        super(repository, passwordEncoder, mailSender);
        this.walletService = walletService;
        this.subDutyService = subDutyService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Expert createExpert(ExpertDtoRequest dto, String siteURL) throws IOException {
        validateExpertDtoRequest(dto);
        Expert expert = Expert.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .wallet((walletService.save(Wallet.builder().creditOfWallet(0).build())))
                .role(Role.ROLE_EXPERT)
                .registrationStatus(NEW)
                .whenExpertRegistered(LocalDate.now())
                .image(setImageForExpert(dto.getPathName()))
                .fieldOfEndeavor(dto.getFieldOfEndeavor())
                .build();
        register(expert, siteURL);
        return repository.save(expert);
    }


    @Override
    public void averageStarOfExpert(Expert expert) {
        double star = repository.averageStarOfExpert(expert);
        expert.setStar(star);
        repository.save(expert);
    }

    @Override
    public void updateRegistrationStatusForSpecificExpert(Expert expert) {
        if (expert == null) {
            throw new NullInputException("Expert must not be null");
        }
        expert.setRegistrationStatus(RegistrationStatus.ACCEPTED);
        repository.save(expert);
    }

    @Override
    public void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty) {
        if (expert == null || subDuty == null) {
            throw new NullInputException("Expert or sub duty cannot be null");
        }
        subDuty.getExperts().remove(expert);
        subDutyService.save(subDuty);
    }

    @Override
    public List<Expert> showExpert() {
        return repository.findAll();
    }

    @Override
    public void saveImageToFolder(byte[] imageData, String folderPath, String fileName) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Path imagePath = Paths.get(folderPath, fileName + ".jpg");
        Files.write(imagePath, imageData);
    }

    @Override
    public byte[] setImageForExpert(String pathname) throws IOException {
        if (pathname == null) {
            throw new NullInputException("Pathname cannot be null");
        }
        byte[] imageData = Files.readAllBytes(Paths.get(pathname));
        if (imageData.length > 300 * 1024) {
            throw new NotValidSizeException("Image size exceeds the limit of 300 KB");
        }
        return imageData;
    }

    @Override
    public double seeStarOfOrder(CustomerOrder customerOrder) {
        return repository.seeStarOfOrder(customerOrder);
    }


    @Override
    public <S extends Expert> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Expert> findById(Integer integer) {
        return repository.findById(integer);
    }

    @Override
    public List<Expert> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Expert> findAllById(Iterable<Integer> integers) {
        return repository.findAllById(integers);
    }

    @Override
    public List<Expert> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Expert> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    @Override
    public void delete(Expert entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Expert> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Expert getReferenceById(Integer integer) {
        return repository.findById(integer).orElseThrow(() -> new NullInputException("null"));
    }
}

