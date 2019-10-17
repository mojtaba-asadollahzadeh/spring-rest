package com.app.restApplication.mappers.requests;

import com.app.restApplication.dtos.requests.UserReqDTO;
import com.app.restApplication.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserRequestMapper {

    UserReqDTO toUserReqDTO(User user);
    // request User : DTO
    User toUserDTO(UserReqDTO userReqDTO);
}
