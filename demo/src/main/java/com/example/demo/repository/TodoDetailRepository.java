package com.example.demo.repository;

import com.example.demo.entity.Todo;
import com.example.demo.entity.TodoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoDetailRepository extends JpaRepository<TodoDetail, Long> {

    Optional<TodoDetail> findByTodo(Todo todo);

}
