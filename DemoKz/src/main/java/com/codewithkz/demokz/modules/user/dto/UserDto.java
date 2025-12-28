package com.codewithkz.demokz.modules.user.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String name;
    private String email;
}
