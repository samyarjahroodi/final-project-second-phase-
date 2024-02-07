package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.payment.Card;
import finalproject.finalproject.service.dto.request.CardDtoRequest;
import finalproject.finalproject.service.dto.response.CardDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    Card requestDtoToModel(CardDtoRequest cardDtoRequest);

    CardDtoResponse modelToDto(Card card);

}
