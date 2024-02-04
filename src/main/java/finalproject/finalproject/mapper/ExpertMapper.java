package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.user.Expert;
import finalproject.finalproject.service.dto.request.ExpertDtoRequest;
import finalproject.finalproject.service.dto.response.UserDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpertMapper {

    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    Expert requestDtoToModel(ExpertDtoRequest expertDtoRequest);

    UserDtoResponse modelToDto(Expert expert);

}
