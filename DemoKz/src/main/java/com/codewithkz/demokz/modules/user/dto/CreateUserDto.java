package com.codewithkz.demokz.modules.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String name;
    private String email;
    private String password;
}

