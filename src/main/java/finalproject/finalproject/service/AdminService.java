package finalproject.finalproject.service;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.request.SearchForPerson;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface AdminService {
    Duty createDuty(DutyDtoRequest dto);

    SubDuty createSubDuty(SubDutyDtoRequest dto, Duty duty);

    void deleteDuty(Duty duty);

    List<Duty> showDuties();

    List<SubDuty> showSubDuties();

    void updateDetailsForSubDuty(SubDutyDtoRequest dto, SubDuty subDuty);

    void addSubDutyToDutyByAdmin(Duty duty, List<SubDuty> subDuties);

    void deleteSubDutyFromTheExistDuty(SubDuty subDuty);

    void deleteSubDutyOFTheSpecificExpert(Expert expert, SubDuty subDuty);

    void deleteExpert(Expert expert);

    void addSubDutyToNewExpert(Expert expert, SubDuty subDuty);

    void changeTheStatusOfExpert(Expert expert);

    List<Person> search(SearchForPerson search);
}
