package com.codewithkz.demokz.modules.user;


import com.codewithkz.demokz.modules.user.dto.CreateUserDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;
import com.codewithkz.demokz.modules.user.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void getUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserDto> users = userService.GetUsers();

        assertThat(users).isNotNull();
        assertThat(users).isEmpty();

        Mockito.verify(userRepository).findAll();
    }

    @Test
    void createUser() {

        CreateUserDto entity = new CreateUserDto();
        entity.setEmail("test@gmail.com");
        entity.setName("test");
        entity.setPassword("123123");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("test");
        user.setPassword("123123");

        UserDto userDto = new UserDto();
        userDto.setEmail("test@gmail.com");
        userDto.setName("test");


        Mockito.when(userMapper.toEntity(entity)).thenReturn(user);
        Mockito.when(userMapper.toDto(user)).thenReturn(userDto);
        Mockito.when(userRepository.save(user)).thenReturn(user);


        UserDto result = userService.CreateUser(entity);


        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(entity.getEmail());


        Mockito.verify(userMapper).toEntity(entity);
        Mockito.verify(userMapper).toDto(user);
        Mockito.verify(userRepository).save(user);
    }




}
