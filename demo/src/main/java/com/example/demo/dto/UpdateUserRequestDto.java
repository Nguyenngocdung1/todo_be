package com.example.demo.dto;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String email;
    private UserDetailDto detail;
}
