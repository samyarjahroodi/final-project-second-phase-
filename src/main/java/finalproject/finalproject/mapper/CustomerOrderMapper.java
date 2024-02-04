package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.operation.CustomerOrder;
import finalproject.finalproject.service.dto.request.CustomerOrderDtoRequest;
import finalproject.finalproject.service.dto.response.CustomerOrderDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {
    CustomerOrderMapper INSTANCE = Mappers.getMapper(CustomerOrderMapper.class);

    CustomerOrder requestDtoToModel(CustomerOrderDtoRequest customerOrderDtoRequest);

    CustomerOrderDtoResponse modelToDto(CustomerOrder customerOrder);

}
