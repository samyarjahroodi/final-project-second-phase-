package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.duty.Duty;
import finalproject.finalproject.service.dto.request.DutyDtoRequest;
import finalproject.finalproject.service.dto.response.DutyDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DutyMapper {
    DutyMapper INSTANCE = Mappers.getMapper(DutyMapper.class);

    Duty requestDtoToModel(DutyDtoRequest dutyDtoRequest);

    DutyDtoResponse modelToDto(Duty duty);

}
