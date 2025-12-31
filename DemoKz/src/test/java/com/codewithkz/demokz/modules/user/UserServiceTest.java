package com.codewithkz.demokz.modules.user;


import com.codewithkz.demokz.modules.user.dto.CreateUserDto;
import com.codewithkz.demokz.modules.user.dto.UserDto;
import com.codewithkz.demokz.modules.user.entity.User;
import com.codewithkz.demokz.modules.user.mapper.UserMapper;
import com.codewithkz.demokz.modules.user.repository.UserRepository;
import com.codewithkz.demokz.modules.user.service.UserService;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        var users = List.of(
                new User(1L, "test1", "test1@gmail.com", "123123"),
                new User(2L, "test2", "test2@gmail.com", "123123"),
                new User(3L, "test3", "test3@gmail.com", "123123")
        );

        var usersDto = List.of(
                new UserDto(1L, "test1", "test1@gmail.com"),
                new UserDto(2L, "test2", "test2@gmail.com"),
                new UserDto(3L, "test3", "test3@gmail.com")
        );
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userMapper.toDtoList(users)).thenReturn(usersDto);

        List<UserDto> result = userService.GetUsers();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1L, result.getFirst().getId());
        assertEquals("test1@gmail.com", result.getFirst().getEmail());

        Mockito.verify(userRepository).findAll();
        Mockito.verify(userMapper).toDtoList(Mockito.any());
    }

    @Test
    void shouldCreateUser() {

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


        assertNotNull(result);
        assertEquals(entity.getEmail(), result.getEmail());


        Mockito.verify(userMapper).toEntity(entity);
        Mockito.verify(userMapper).toDto(user);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void shouldThrowDuplicateExceptionWhenCreateUser() {

        CreateUserDto entity = new CreateUserDto();
        entity.setEmail("test@gmail.com");
        entity.setName("test");
        entity.setPassword("123123");

        Mockito.when(userRepository.findByEmail(entity.getEmail()))
                .thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.CreateUser(entity)
        );


        Mockito.verify(userRepository).findByEmail(entity.getEmail());

        assertEquals("Email already exists", exception.getMessage());


        Mockito.verify(userRepository).findByEmail(entity.getEmail());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(userMapper, Mockito.never()).toEntity(Mockito.any());
        Mockito.verify(userMapper, Mockito.never()).toDto(Mockito.any());

    }




}
