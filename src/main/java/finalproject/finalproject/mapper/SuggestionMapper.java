package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.operation.Suggestion;
import finalproject.finalproject.service.dto.request.SuggestionDtoRequest;
import finalproject.finalproject.service.dto.response.SuggestionDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SuggestionMapper {
    SuggestionMapper INSTANCE = Mappers.getMapper(SuggestionMapper.class);

    Suggestion requestDtoToModel(SuggestionDtoRequest suggestionDtoRequest);

    SuggestionDtoResponse modelToDto(Suggestion suggestion);

}
