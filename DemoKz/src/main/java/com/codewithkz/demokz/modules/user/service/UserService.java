package com.codewithkz.demokz.modules.user.service;


import com.codewithkz.demokz.common.exception.DuplicateException;
import com.codewithkz.demokz.common.exception.NotFoundException;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> GetUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }



}
