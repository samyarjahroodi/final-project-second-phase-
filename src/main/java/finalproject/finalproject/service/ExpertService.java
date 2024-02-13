package finalproject.finalproject.service;


import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public interface ExpertService extends PersonService<Expert>, BaseService<Expert, Integer> {
    void createExpert(ExpertDtoRequest dto) throws IOException;

    void averageStarOfExpert(Expert expert);

    void updateRegistrationStatusForSpecificExpert(Expert expert);

    void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty);

    List<Expert> showExpert();

    void saveImageToFolder(byte[] imageData, String folderPath, String fileName) throws IOException;

    byte[] setImageForExpert(String pathname) throws IOException;

    double seeStarOfOrder(CustomerOrder customerOrder);
}
