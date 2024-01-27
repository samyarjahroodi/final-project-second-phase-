package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.user.Expert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class ExpertServiceImplTest extends BaseTest {

    @Test
    void averageStarOfExpert() throws IOException {
        Expert expert = createExpert();
        double v = expertService.averageStarOfExpert(expert);
        assertEquals(null, v);
    }

    @Test
    void showExpert() {
        List<Expert> allExperts = expertRepository.findAll();
        int expectedSize = 0;
        assertEquals(expectedSize, allExperts.size(), "Number of experts does not match expected size");
    }

    @Test
    void createExpertForTest() throws IOException {
        Expert expert = createExpert();
        assertEquals(1, expert.getId());
    }

    @Test
    void saveImageToFolder() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\Samyar\\Desktop\\images.jpg"));
        String folderPath = "E:\\final-project\\src\\main\\resources";
        String fileName = "testImage";

        expertService.setImageForExpert("C:\\Users\\Samyar\\Desktop\\images.jpg");
        expertService.saveImageToFolder(bytes, folderPath, fileName);

        Path imagePath = Paths.get(folderPath, fileName + ".jpg");
        assertTrue(Files.exists(imagePath), "Image file not created in the specified folder");
        /* Files.deleteIfExists(imagePath);*/
    }


    @Test
    void setImageForExpert() throws IOException {
        expertService.setImageForExpert("C:\\Users\\Samyar\\Desktop\\images.jpg");
    }
}