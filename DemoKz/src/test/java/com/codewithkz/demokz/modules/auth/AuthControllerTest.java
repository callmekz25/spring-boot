package com.codewithkz.demokz.modules.auth;

import com.codewithkz.demokz.common.exception.BadRequestException;
import com.codewithkz.demokz.modules.auth.controller.AuthController;
import com.codewithkz.demokz.modules.auth.dto.LoginDto;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.auth.dto.TokenResponseDto;
import com.codewithkz.demokz.modules.auth.services.AuthService;
import com.codewithkz.demokz.modules.auth.services.JwtService;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @Test
    public void shouldLoginSuccessfully() throws Exception {
        LoginDto loginDto = new LoginDto("test@gmail.com", "123123");


        var accessToken = "accessToken";
        var refreshToken = "refreshToken";

        TokenResponseDto dto = new TokenResponseDto(accessToken, refreshToken);

        Mockito.when(authService.login(loginDto)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.accessToken").value(accessToken))
                .andExpect(jsonPath("$.data.refreshToken").value(refreshToken))
                .andDo(print());

    }


    @Test
    public void shouldLoginFailCauseInvalidEmail() throws Exception {
        LoginDto loginDto = new LoginDto("test@gmail.com", "123123");


        Mockito.when(authService.login(loginDto)).thenThrow(new BadRequestException("Invalid email"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid email"))
                .andDo(print());

    }


    @Test
    public void shouldLoginFailCauseInvalidPassword() throws Exception {
        LoginDto loginDto = new LoginDto("test@gmail.com", "123123");


        Mockito.when(authService.login(loginDto)).thenThrow(new BadRequestException("Invalid email or password"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid email or password"))
                .andDo(print());

    }


    @Test
    public void shouldRegisterSuccessfully() throws Exception {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "123123");
        UserDto userDto = new UserDto(1L, registerDto.getName(), registerDto.getEmail());

        Mockito.when(authService.register(registerDto)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value(userDto.getId()))
                .andExpect(jsonPath("$.data.email").value(userDto.getEmail()))
                .andExpect(jsonPath("$.data.name").value(userDto.getName()))
                .andDo(print());
    }


    @Test
    public void shouldRegisterFailCauseEmailUsed() throws Exception {
        RegisterDto registerDto = new RegisterDto("test", "test@gmail.com", "123123");

        Mockito.when(authService.register(registerDto)).thenThrow(new BadRequestException("Email already in use"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email already in use"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }


}
