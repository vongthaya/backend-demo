package com.vongthaya.backenddemo.mapper;

import com.vongthaya.backenddemo.dto.UserResponse;
import com.vongthaya.backenddemo.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

}
