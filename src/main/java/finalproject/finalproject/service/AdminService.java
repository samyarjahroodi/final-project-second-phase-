package finalproject.finalproject.service;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.Entity.user.Admin;
import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.Entity.user.Person;
import finalproject.finalproject.service.dto.request.*;
import finalproject.finalproject.service.dto.response.ReportForManagerDtoResponse;
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

    boolean existsByUsername(String name);

    Admin save(Admin admin);

    List<SubDuty> historyOfSubDutyForCustomerOrExpertForManger(String username);

    List<CustomerOrder> giveCustomerOrdersSortedInformationToManager(CustomerOrdersSortedInformationToManagerDtoRequest dto);

    ReportForManagerDtoResponse reportForManagerFromPersons(String username);
}
