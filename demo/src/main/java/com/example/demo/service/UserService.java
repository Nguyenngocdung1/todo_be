package com.example.demo.service;

import com.example.demo.dto.UpdateUserRequestDto;
import com.example.demo.dto.UserCreateRequestDto;
import com.example.demo.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAll();

    UserResponseDto getDetail(Long id);

    void delete(Long id);

    UserResponseDto update(Long id, UpdateUserRequestDto dto);

    void updateStatusAllTodos(Long userId, Boolean completed);

    UserResponseDto create(UserCreateRequestDto dto);
}
