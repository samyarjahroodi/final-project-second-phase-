package finalproject.finalproject.mapper;

import finalproject.finalproject.Entity.user.Customer;
import finalproject.finalproject.service.dto.request.UserDtoChangePasswordRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequest;
import finalproject.finalproject.service.dto.request.UserDtoRequestToLogin;
import finalproject.finalproject.service.dto.response.UserDtoResponse;
import finalproject.finalproject.service.dto.response.UserDtoResponseToLogin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer requestDtoToModelToLogin(UserDtoRequestToLogin userDtoRequestToLogin);

    UserDtoResponseToLogin modelToDtoToLogin(Customer customer);

    Customer requestDtoToModel(UserDtoRequest userDtoRequest);

    UserDtoResponse modelToDto(Customer customer);

    Customer requestDtoToModelToChangePassword(UserDtoChangePasswordRequest userDtoRequestToLogin);

    UserDtoResponseToLogin modelToDtoToChangePassword(Customer customer);

}
