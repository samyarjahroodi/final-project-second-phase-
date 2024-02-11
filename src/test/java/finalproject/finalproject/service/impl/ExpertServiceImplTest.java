package finalproject.finalproject.service.impl;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.operation.Status;
import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.request.CommentDtoRequest;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
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
        assertEquals(1, size);
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

    @Test
    void averageStar() throws IOException {
        Expert expert = createExpert();
        SubDuty subDuty = createSubDuty(100);
        Duty duty = createDuty();
        adminService.addSubDutyToDutyByAdmin(duty, Collections.singletonList(subDuty));
        CustomerOrder customerOrder = createCustomerOrder(150, LocalDate.now(), Status.STARTED,
                LocalDate.of(2024, 02, 12), subDuty.getPrice(), duty, subDuty);
        Suggestion suggestion = createSuggestion(120,  LocalDate.of(2024,02,12));
        SuggestionDtoRequest suggestionDto =
                createSuggestionDto(120, LocalDate.now(), LocalDate.of(2024, 02, 12), 2);
        suggestionService.createSuggestionForExpert(expert,suggestionDto,customerOrder);
        commentService.addCommentForCustomerId(customerOrder, createCommentDtoRequest());
        expertService.averageStarOfExpert(expert);
        assertEquals(3.4,expert.getStar());
    }
}