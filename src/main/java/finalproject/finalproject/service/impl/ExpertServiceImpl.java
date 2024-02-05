package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.exception.NotValidFormatFileException;
import finalproject.finalproject.exception.NotValidSizeException;
import finalproject.finalproject.exception.NullInputException;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.ExpertService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
@Primary

@Transactional(readOnly = true)
public class ExpertServiceImpl
        extends PersonServiceImpl<Expert, ExpertRepository>
        implements ExpertService {

    private final WalletRepository walletRepository;
    private final SubDutyServiceImpl subDutyService;

    public ExpertServiceImpl(ExpertRepository repository, WalletRepository walletRepository, SubDutyRepository subDutyRepository, SubDutyServiceImpl subDutyService) {
        super(repository);
        this.walletRepository = walletRepository;
        this.subDutyService = subDutyService;
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
        if (!pathname.toLowerCase().endsWith(".jpg")) {
            throw new NotValidFormatFileException("Only JPG images are allowed");
        }
        byte[] imageData = Files.readAllBytes(Paths.get(pathname));
        if (imageData.length > 300 * 1024) {
            throw new NotValidSizeException("Image size exceeds the limit of 300 KB");
        }
        return imageData;
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
        return repository.getReferenceById(integer);
    }
}

