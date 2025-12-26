package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto getDetail(@PathVariable Long id) {
        return userService.getDetail(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequestDto dto
    ) {
        return userService.update(id, dto);
    }

    @PutMapping("/{id}/todos/status")
    public void updateTodosStatus(
            @PathVariable Long id,
            @RequestBody UpdateTodosStatusRequestDto dto
    ) {
        userService.updateStatusAllTodos(id, dto.getCompleted());
    }

    @PostMapping
    public UserResponseDto create(@RequestBody UserCreateRequestDto dto) {
        return userService.create(dto);
    }
}
