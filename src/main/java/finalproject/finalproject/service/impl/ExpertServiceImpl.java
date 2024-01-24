package finalproject.finalproject.service.impl;


import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.utility.Wallet;
import finalproject.finalproject.repository.ExpertRepository;
import finalproject.finalproject.repository.WalletRepository;
import finalproject.finalproject.service.ExpertService;
import finalproject.finalproject.service.dto.ExpertDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static finalproject.finalproject.Entity.user.RegistrationStatus.AWAITING_CONFIRMATION;

@Service
@Transactional(readOnly = true)
public class ExpertServiceImpl
        extends PersonServiceImpl<Expert, ExpertRepository>
        implements ExpertService {
    private final WalletRepository walletRepository;

    public ExpertServiceImpl(ExpertRepository repository, WalletRepository walletRepository) {
        super(repository);
        this.walletRepository = walletRepository;
    }

    @Override
    public double averageStarOfExpert(Expert expert) {
        return repository.averageStarOfExpert(expert);
    }


    public List<Expert> showExpert() {
        return repository.findAll();
    }

    public void createExpert(ExpertDto dto) {
        Expert expert = new Expert();
        expert.setFirstname(dto.getFirstname());
        expert.setLastname(dto.getLastname());
        expert.setEmail(dto.getEmail());
        expert.setPassword(dto.getPassword());
        expert.setUsername(dto.getUsername());
        Wallet wallet = new Wallet();
        wallet.setCreditOfWallet(0);
        walletRepository.save(wallet);
        expert.setWallet(wallet);
        expert.setRegistrationStatus(AWAITING_CONFIRMATION);
        expert.setStar(repository.averageStarOfExpert(expert));
        expert.setWhenExpertRegistered(LocalDate.now());
        expert.setImage(dto.getProfileImage());
        repository.save(expert);
    }

/*    public static void getImage(Expert expert) {
        if (expert != null && expert.getImage() != null) {
            String filePath = "F:\\finalProject\\src\\main\\resources\\image.jpg";
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(expert.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void saveImageToFolder(byte[] imageData, String folderPath, String fileName) throws IOException {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Path imagePath = Paths.get(folderPath, fileName + ".jpg");
        Files.write(imagePath, imageData);
    }


    public byte[] setImageForExpert(ExpertDto dto) throws IOException {
        String imagePath = dto.getPathName();
        if (!imagePath.toLowerCase().endsWith(".jpg")) {
            throw new IllegalArgumentException("Only JPG images are allowed");
        }
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));
        if (imageData.length > 300 * 1024) {
            throw new IllegalArgumentException("Image size exceeds the limit of 300 KB");
        }
        return imageData;
    }

}

