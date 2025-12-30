package com.codewithkz.demokz.modules.user.service;


import com.codewithkz.demokz.common.exception.DuplicateException;
import com.codewithkz.demokz.common.exception.NotFoundException;
import com.codewithkz.demokz.modules.user.dto.CreateUserDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean ExistedEmail(String email) {
        log.info("Checking if email {} exists", email);
        return userRepository.findByEmail(email).isPresent();
    }

    public User GetById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found, id={}", id);
            return new NotFoundException("User not found");
        });
    }


    public List<UserDto> GetUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    public UserDto CreateUser(CreateUserDto dto) {

        boolean existedEmail = ExistedEmail(dto.getEmail());

        if(existedEmail) {
            log.warn("Email {} already exists", dto.getEmail());
            throw new DuplicateException("Email already exists");
        }

        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        log.info("Created user {}", user);
        return userMapper.toDto(user);
    }


}
