package com.codewithkz.demokz.modules.auth;

import com.codewithkz.demokz.common.exception.BadRequestException;
import com.codewithkz.demokz.modules.auth.dto.LoginDto;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.auth.dto.TokenResponseDto;
import com.codewithkz.demokz.modules.auth.services.AuthService;
import com.codewithkz.demokz.modules.auth.services.JwtService;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.Roles;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void shouldRegisterSuccessfully() {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "123123");
        User user = new User(1L, "test", "test@gmail.com", "123123", Roles.ROLE_USER);
        UserDto dto = new UserDto(1L, "test", "test@gmail.com");

        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode("123123")).thenReturn("123123");
        Mockito.when(userMapper.toEntity(registerDto)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(dto);

        UserDto result = authService.register(registerDto);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getName(), result.getName());

        Mockito.verify(userRepository).findByEmail("test@gmail.com");
        Mockito.verify(passwordEncoder).encode("123123");
        Mockito.verify(userMapper).toEntity(registerDto);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(userMapper).toDto(user);

    }

    @Test
    public void shouldRegisterThrowBadRequestException() {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "123123");
        User user = new User(1L, "test", "test@gmail.com", "123123", Roles.ROLE_USER);

        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.register(registerDto));

        assertEquals("Email already in use", exception.getMessage());

        Mockito.verify(userRepository).findByEmail("test@gmail.com");
    }

    @Test
    public void shouldLoginSuccessfully() {
        LoginDto loginDto = new LoginDto("test@gmail.com", "123123");
        User user = new User(1L, "test", "test@gmail.com", "123123", Roles.ROLE_USER);

        var accessToken = "accessToken";
        var refreshToken = "refreshToken";


        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);
        Mockito.when(jwtService.generateAccessToken(user)).thenReturn(accessToken);
        Mockito.when(jwtService.generateRefreshToken(user)).thenReturn(refreshToken);


        TokenResponseDto result = authService.login(loginDto);

        assertNotNull(result);
        assertEquals(accessToken, result.getAccessToken());
        assertEquals(refreshToken, result.getRefreshToken());

        Mockito.verify(userRepository).findByEmail("test@gmail.com");
        Mockito.verify(passwordEncoder).matches(loginDto.getPassword(), user.getPassword());
        Mockito.verify(jwtService).generateAccessToken(user);
        Mockito.verify(jwtService).generateRefreshToken(user);

    }

    @Test
    public void shouldLoginThrowBadRequestExceptionInvalidEmail() {
        LoginDto loginDto = new LoginDto("test@gmail.com", "123123");


        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.login(loginDto));

        assertEquals("Invalid email", exception.getMessage());

        Mockito.verify(userRepository).findByEmail("test@gmail.com");
    }

    @Test
    public void shouldLoginThrowBadRequestExceptionInvalidEmailOrPassword() {
        LoginDto loginDto = new LoginDto("test@gmail.com", "123123");
        User user = new User(1L, "test", "test@gmail.com", "123123", Roles.ROLE_USER);

        Mockito.when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.login(loginDto));

        assertEquals("Invalid email or password", exception.getMessage());

        Mockito.verify(userRepository).findByEmail("test@gmail.com");
        Mockito.verify(passwordEncoder).matches(loginDto.getPassword(), user.getPassword());
    }
}
