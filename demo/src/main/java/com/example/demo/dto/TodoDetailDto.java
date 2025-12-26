package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoDetailDto {
    private Long todoId;
    private String description;
    private LocalDateTime deadline;
    private Integer priority;
}
