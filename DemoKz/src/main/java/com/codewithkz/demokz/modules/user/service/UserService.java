package com.codewithkz.demokz.modules.user.service;


import com.codewithkz.demokz.common.exception.DuplicateException;
import com.codewithkz.demokz.modules.user.dto.CreateUserDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean ExistedEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    public List<UserDto> GetUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    public UserDto CreateUser(CreateUserDto dto) {

        boolean existedEmail = ExistedEmail(dto.getEmail());

        if(existedEmail) {
            throw new DuplicateException("Email already exists");
        }

        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }


}
