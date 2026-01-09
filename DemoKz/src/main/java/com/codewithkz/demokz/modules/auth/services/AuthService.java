package com.codewithkz.demokz.modules.auth.services;

import com.codewithkz.demokz.common.exception.BadRequestException;
import com.codewithkz.demokz.modules.auth.dto.LoginDto;
import com.codewithkz.demokz.modules.auth.dto.TokenResponseDto;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.Roles;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;



    public UserDto register(RegisterDto dto) {
        var user =  userRepository.findByEmail(dto.getEmail());
        if(user.isPresent()) {
            throw new BadRequestException("Email already in use");
        }

        var password = passwordEncoder.encode(dto.getPassword());

        User entity = userMapper.toEntity(dto);
        entity.setRole(Roles.ROLE_ADMIN);
        entity.setPassword(password);

        userRepository.save(entity);

        return userMapper.toDto(entity);
    }

    public TokenResponseDto login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new BadRequestException("Invalid email"));

        var isValid = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if(!isValid) {
            throw new BadRequestException("Invalid email or password");
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return new TokenResponseDto(accessToken, refreshToken);

    }
}
