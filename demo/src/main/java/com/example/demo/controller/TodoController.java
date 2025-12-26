package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.PaginatedResponse;
import com.example.demo.dto.TodoListDto;
import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public ApiResponse<PaginatedResponse<TodoListDto>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword
    ) {
        return ApiResponse.ok(todoService.getAll(page, size, keyword));
    }

    @PostMapping
    public ApiResponse<Todo> create(@RequestBody Map<String, String> body) {
        return ApiResponse.ok(todoService.create(body.get("title")));
    }

    @PutMapping("/{id}")
    public ApiResponse<Todo> update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        return ApiResponse.ok(
                todoService.update(
                        id,
                        (String) body.get("title"),
                        body.get("completed") != null
                                ? Boolean.valueOf(body.get("completed").toString())
                                : null
                )
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        todoService.delete(id);
        return ApiResponse.ok(null);
    }
}
