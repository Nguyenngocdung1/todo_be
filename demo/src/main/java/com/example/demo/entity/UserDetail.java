package com.example.demo.entity;

import com.example.demo.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===== Thông tin cơ bản ===== */

    private String fullName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String avatarUrl;

    /* ===== Địa chỉ ===== */

    private String country;
    private String city;
    private String address;

    /* ===== Thông tin mở rộng ===== */

    @Column(columnDefinition = "TEXT")
    private String bio;

    /**
     * Metadata mở rộng:
     * ví dụ: socialLinks, preferences, settings, v.v.
     */
    @ElementCollection
    @CollectionTable(
            name = "user_detail_metadata",
            joinColumns = @JoinColumn(name = "user_detail_id")
    )
    @MapKeyColumn(name = "meta_key")
    @Column(name = "meta_value")
    private Map<String, String> metadata;

    /* ===== Audit ===== */

    private LocalDateTime updatedAt;

    /* ===== Relation ===== */

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
