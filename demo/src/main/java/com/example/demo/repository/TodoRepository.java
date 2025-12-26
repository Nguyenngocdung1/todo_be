package com.example.demo.repository;

import com.example.demo.dto.TodoListDto;
import com.example.demo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("""
    SELECT new com.example.demo.dto.TodoListDto(
        t.id,
        t.title,
        t.completed
    )
    FROM Todo t
    WHERE (:keyword IS NULL OR :keyword = ''
        OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
    """)
    Page<TodoListDto> findTodoList(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Modifying
    @Query("""
        update Todo t
        set t.completed = :completed
        where t.user.id = :userId
    """)
    int updateStatusByUserId(Long userId, Boolean completed);
}