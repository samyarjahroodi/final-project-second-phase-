package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.duty.SubDuty;
import finalproject.finalproject.service.dto.request.SubDutyDtoRequest;
import finalproject.finalproject.service.dto.response.SubDutyDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubDutyMapper {
    SubDutyMapper INSTANCE = Mappers.getMapper(SubDutyMapper.class);

    SubDuty requestDtoToModel(SubDutyDtoRequest subDutyDtoRequest);

    SubDutyDtoResponse modelToDto(SubDuty subDuty);

}
