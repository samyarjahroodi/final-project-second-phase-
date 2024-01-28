package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.RegistrationStatus;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.repository.SubDutyRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.ExpertService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
@Primary
@Transactional(readOnly = true)
public class ExpertServiceImpl
        extends PersonServiceImpl<Expert, ExpertRepository>
        implements ExpertService {
    private final WalletRepository walletRepository;
    private final SubDutyRepository subDutyRepository;

    public ExpertServiceImpl(ExpertRepository repository, WalletRepository walletRepository, SubDutyRepository subDutyRepository) {
        super(repository);
        this.walletRepository = walletRepository;
        this.subDutyRepository = subDutyRepository;
    }

    @Override
    public void updateRegistrationStatusForSpecificExpert(Expert expert) {
        if (expert == null) {
            throw new IllegalArgumentException("Expert must not be null");
        }
        expert.setRegistrationStatus(RegistrationStatus.ACCEPTED);
        repository.save(expert);
    }

    @Override
    public void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty) {
        if (expert == null || subDuty == null) {
            throw new IllegalArgumentException("Expert or sub duty cannot be null");
        }
        subDuty.getExperts().remove(expert);
        subDutyRepository.save(subDuty);
    }


    public List<Expert> showExpert() {
        return repository.findAll();
    }


    public void saveImageToFolder(byte[] imageData, String folderPath, String fileName) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Path imagePath = Paths.get(folderPath, fileName + ".jpg");
        Files.write(imagePath, imageData);
    }


    public byte[] setImageForExpert(String pathname) throws IOException {
        if (!pathname.toLowerCase().endsWith(".jpg")) {
            throw new IllegalArgumentException("Only JPG images are allowed");
        }
        byte[] imageData = Files.readAllBytes(Paths.get(pathname));
        if (imageData.length > 300 * 1024) {
            throw new IllegalArgumentException("Image size exceeds the limit of 300 KB");
        }
        return imageData;
    }

}

