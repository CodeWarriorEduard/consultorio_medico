package com.rafael.consultorio_medico_actividad.mapper;

import com.rafael.consultorio_medico_actividad.dto.response.UserLoginDTOResponse;
import com.rafael.consultorio_medico_actividad.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserLoginDTOResponse toUserLoginDtoResponse(User user, String token);

}
