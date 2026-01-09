package com.codewithkz.demokz.modules.user.mapper;

import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterDto dto);
    List<UserDto> toDtoList(List<User> users);
}
