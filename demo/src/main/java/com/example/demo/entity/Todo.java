package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /* ================== RELATION ================== */

    // Todo - TodoDetail (1-1)
    @OneToOne(mappedBy = "todo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TodoDetail detail;

    // Todo - User (N-1)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
