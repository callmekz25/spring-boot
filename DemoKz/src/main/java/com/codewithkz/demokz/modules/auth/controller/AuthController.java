package com.codewithkz.demokz.modules.auth.controller;

import com.codewithkz.demokz.common.response.ApiResponse;
import com.codewithkz.demokz.modules.auth.dto.LoginDto;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.auth.dto.TokenResponseDto;
import com.codewithkz.demokz.modules.auth.services.AuthService;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody RegisterDto dto) {
        var result = authService.register(dto);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@RequestBody LoginDto dto) {
        var result = authService.login(dto);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
