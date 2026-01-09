package com.codewithkz.demokz.modules.user;


import com.codewithkz.demokz.common.exception.DuplicateException;
import com.codewithkz.demokz.modules.user.controller.UserController;
import com.codewithkz.demokz.modules.auth.dto.RegisterDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnOkWhenGetUsers() throws Exception {
        UserDto user1 = new UserDto(1L, "test1", "test1@gmail.com");
        UserDto user2 = new UserDto(2L, "test2", "test2@gmail.com");
        UserDto user3 = new UserDto(3L, "test3", "test3@gmail.com");

        List<UserDto> users = List.of(user1, user2, user3);
        Mockito.when(userService.GetUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].email").value("test1@gmail.com"))
                .andDo(print());

    }

    @Test
    void shouldReturnCreatedWhenRegister() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test1@gmail.com");
        dto.setName("test1");
        dto.setPassword("123123");

        UserDto user = new UserDto();
        user.setEmail("test1@gmail.com");
        user.setName("test1");

        Mockito.when(userService.CreateUser(Mockito.any(RegisterDto.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("test1@gmail.com"))
                .andExpect(jsonPath("$.data.name").value("test1"))
                .andDo(print());
    }

    @Test
    void shouldReturn400WhenCreateUser() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test1@gmail.com");
        dto.setPassword("123123");

        UserDto user = new UserDto();
        user.setEmail("test1@gmail.com");
        user.setName("test1");

        Mockito.when(userService.CreateUser(Mockito.any(RegisterDto.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andDo(print());
    }

    @Test
    void shouldReturn409WhenCreateUser() throws Exception {
        RegisterDto dto = new RegisterDto();
        dto.setEmail("test1@gmail.com");
        dto.setName("test1");
        dto.setPassword("123123");

        Mockito.when(userService.CreateUser(Mockito.any(RegisterDto.class)))
                .thenThrow(new DuplicateException("Email already exists"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email already exists"))
                .andDo(print());
    }

}
