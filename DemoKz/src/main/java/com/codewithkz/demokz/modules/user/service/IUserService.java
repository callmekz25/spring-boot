package com.codewithkz.demokz.modules.user.service;

import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;

import java.util.List;

public interface IUserService {
    boolean ExistedEmail(String email);
    User GetById(Long id);
    List<UserDto> GetUsers();
}
