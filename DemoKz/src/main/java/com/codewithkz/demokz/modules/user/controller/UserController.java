package com.codewithkz.demokz.modules.user.controller;

import com.codewithkz.demokz.common.response.ApiResponse;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> GetUsers() {
        List<UserDto> users = userService.GetUsers();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(users));
    }




}
