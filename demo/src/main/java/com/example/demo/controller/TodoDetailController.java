package com.example.demo.controller;

import com.example.demo.dto.TodoDetailDto;
import com.example.demo.dto.TodoDetailRequest;
import com.example.demo.entity.TodoDetail;
import com.example.demo.service.TodoDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TodoDetailController {

    private final TodoDetailService todoDetailService;

    @GetMapping("/{id}/detail")
    public TodoDetailDto getDetail(@PathVariable Long id) {
        return todoDetailService.getDetail(id);
    }

    @PutMapping("/{id}/detail")
    public TodoDetailDto updateDetail(
            @PathVariable Long id,
            @RequestBody TodoDetailRequest request
    ) {
        return todoDetailService.updateDetail(id, request);
    }
}
