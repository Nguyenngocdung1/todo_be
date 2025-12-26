package com.example.demo.service;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.PaginatedResponse;
import com.example.demo.dto.TodoDetailRequest;
import com.example.demo.dto.TodoListDto;
import com.example.demo.entity.Todo;
import com.example.demo.entity.TodoDetail;
import com.example.demo.repository.TodoDetailRepository;
import com.example.demo.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoDetailRepository todoDetailRepository;

    public PaginatedResponse<TodoListDto> getAll(
            int page,
            int size,
            String keyword
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<TodoListDto> todoPage =
                todoRepository.findTodoList(keyword, pageable);

        return new PaginatedResponse<>(
                todoPage.getContent(),
                page,
                size,
                todoPage.getTotalPages(),
                todoPage.getTotalElements()
        );
    }



    @Transactional
    public Todo create(String title) {

        Todo todo = Todo.builder()
                .title(title)
                .completed(false)
                .build();

        // tạo Todo trước
        todoRepository.save(todo);

        // tạo TodoDetail mặc định
        TodoDetail detail = TodoDetail.builder()
                .todo(todo)
                .description(null)
                .deadline(null)
                .priority(null)
                .build();

        todoDetailRepository.save(detail);

        // gán ngược lại (optional nhưng nên có)
        todo.setDetail(detail);

        return todo;
    }


    public Todo update(Long id, String title, Boolean completed) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        if (title != null) {
            todo.setTitle(title);
        }
        if (completed != null) {
            todo.setCompleted(completed);
        }

        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
