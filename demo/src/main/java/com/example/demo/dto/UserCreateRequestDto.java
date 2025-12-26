package com.example.demo.dto;

import lombok.Data;

@Data
public class UserCreateRequestDto {

    private String username;

    private String email;

    private String password;

    private UserDetailDto detail;
}
