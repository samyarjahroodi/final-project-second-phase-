package finalproject.finalproject.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;

import java.io.IOException;
import java.util.List;

public interface AdminService   {
    Duty createDuty(DutyDtoRequest dto);

    SubDuty createSubDuty(SubDutyDtoRequest dto, Duty duty);

    void deleteDuty(Duty duty);

    List<Duty> showDuties();

    List<SubDuty> showSubDuties();

    void updateDetailsForSubDuty(SubDutyDtoRequest dto, SubDuty subDuty);

    void createExpert(ExpertDtoRequest dto) throws IOException;

    void addSubDutyToDutyByAdmin(Duty duty, List<SubDuty> subDuties);

    void deleteSubDutyFromTheExistDuty(SubDuty subDuty);

    void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty);

    void deleteExpert(Expert expert);

    void addSubDutyToNewExpert(Expert expert, SubDuty subDuty);

    void changeTheStatusOfExpert(Expert expert);
}
