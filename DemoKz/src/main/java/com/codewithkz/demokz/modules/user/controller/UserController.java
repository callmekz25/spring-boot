package com.codewithkz.demokz.modules.user.controller;

import com.codewithkz.demokz.common.response.ApiResponse;
import com.codewithkz.demokz.modules.user.dto.CreateUserDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> GetUsers() {
        List<UserDto> users = userService.GetUsers();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> CreateUser(@RequestBody CreateUserDto dto) {
        UserDto user = userService.CreateUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(user));
    }


}
