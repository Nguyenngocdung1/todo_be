package com.example.demo.service;

import com.example.demo.dto.TodoDetailDto;
import com.example.demo.dto.TodoDetailRequest;
import com.example.demo.entity.Todo;
import com.example.demo.entity.TodoDetail;
import com.example.demo.repository.TodoDetailRepository;
import com.example.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoDetailService {

    private final TodoRepository todoRepository;
    private final TodoDetailRepository todoDetailRepository;

    /**
     * Lấy chi tiết todo
     */
    @Transactional(readOnly = true)
    public TodoDetailDto getDetail(Long todoId) {
        Todo todo = findTodo(todoId);

        return todoDetailRepository.findByTodo(todo)
                .map(detail -> new TodoDetailDto(
                        todo.getId(),
                        detail.getDescription(),
                        detail.getDeadline(),
                        detail.getPriority()
                ))
                .orElse(null);
    }


    /**
     * CẬP NHẬT detail
     * - Bắt buộc detail đã tồn tại
     * - Không tự tạo mới
     */
    @Transactional
    public TodoDetailDto updateDetail(Long todoId, TodoDetailRequest request) {
        Todo todo = findTodo(todoId);

        TodoDetail detail = todoDetailRepository.findByTodo(todo)
                .orElseThrow(() ->
                        new RuntimeException("Todo detail not found for todoId: " + todoId)
                );

        applyRequest(detail, request);

        TodoDetail saved = todoDetailRepository.save(detail);

        return new TodoDetailDto(
                todo.getId(),
                saved.getDescription(),
                saved.getDeadline(),
                saved.getPriority()
        );
    }


    // ================= PRIVATE METHODS =================

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() ->
                        new RuntimeException("Todo not found: " + todoId)
                );
    }

    private TodoDetail createEmptyDetail(Todo todo) {
        return TodoDetail.builder()
                .todo(todo)
                .build();
    }

    private void applyRequest(TodoDetail detail, TodoDetailRequest request) {
        detail.setDescription(request.getDescription());
        detail.setDeadline(request.getDeadline());
        detail.setPriority(request.getPriority());
    }
}
