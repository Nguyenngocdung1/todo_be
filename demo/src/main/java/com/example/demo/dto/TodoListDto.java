package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoListDto {
    private Long id;
    private String title;
    private Boolean completed;
}
