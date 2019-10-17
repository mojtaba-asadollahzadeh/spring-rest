package com.app.restApplication.mappers.responses;

import com.app.restApplication.dtos.responses.UserResDTO;
import com.app.restApplication.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserResponseMapper {

    UserResDTO toUserResDTO(User user);
    // fetch a user : DTO
    User toUserDTO(UserResDTO user);
    // fetch all users : DTO
    List<UserResDTO> toUsers(List<User> users);
}
