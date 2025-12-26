package com.example.demo.dto;

import com.example.demo.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class UserDetailDto {
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String city;
    private String address;
    private Map<String, String> metadata;
}
