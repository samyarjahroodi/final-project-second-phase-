package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "finalproject.finalproject")
class ExpertServiceImplTest extends BaseTest {

    @Test
    void showExpert() {
        List<Expert> allExperts = expertRepository.findAll();
        int expectedSize = 0;
        assertEquals(expectedSize, allExperts.size(), "Number of experts does not match expected size");
    }

    @Test
    void showExpertForTest() throws IOException {
        Expert expert = createExpert();
        int size = expertService.showExpert().size();
        assertEquals(1,size);
    }

    @Test
    void setImageForExpert_ValidImage() throws IOException {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\images.jpg";

        byte[] imageData = expertService.setImageForExpert(imagePath);

        assertNotNull(imageData);
    }
    @Test
    void deleteSubDutyOFTheSpecificExpert() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(4000);
        adminService.addSubDutyToNewExpert(expert, subDuty);
        expertService.deleteSubDutyOFTheSpecificExpert(expert, subDuty);
        assertEquals(0, subDuty.getExperts().size());
    }

    @Test
    void testDeleteSubDutyOFTheSpecificExpertWithNullExpert() {
        SubDuty subDuty = createSubDuty(3000);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> expertService.deleteSubDutyOFTheSpecificExpert(null, subDuty)
        );
        assertEquals("Expert or sub duty cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteSubDutyOFTheSpecificExpertWithNullSubDuty() throws IOException {
        Expert expert = createExpert();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> expertService.deleteSubDutyOFTheSpecificExpert(expert, null)
        );
        assertEquals("Expert or sub duty cannot be null", exception.getMessage());
    }

    @Test
    void setImageForExpertInvalidImageExtension() {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\image.png";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> expertService.setImageForExpert(imagePath));
        assertEquals("Only JPG images are allowed", exception.getMessage());
    }

    @Test
    void setImageForExpertImageSizeExceedsLimit() {
        String imagePath = "C:\\Users\\Samyar\\Desktop\\large_image.jpg";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> expertService.setImageForExpert(imagePath));
        assertEquals("Image size exceeds the limit of 300 KB", exception.getMessage());
    }

    @Test
    void saveImageToFolder() throws IOException {
        byte[] imageData = Files.readAllBytes(Paths.get("C:\\Users\\Samyar\\Desktop\\images.jpg"));
        String folderPath = "E:\\final-project\\src\\main\\resources";
        String fileName = "testImage";
        expertService.saveImageToFolder(imageData, folderPath, fileName);
        Path imagePath = Paths.get(folderPath, fileName + ".jpg");
        assertTrue(Files.exists(imagePath), "Image file not created in the specified folder");
    }
}