package com.napkinsgray.napkins24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 사용자 정보를 관리하는 User 엔티티
 * - 생성일(createdAt)과 수정일(updatedAt)을 자동으로 관리합니다.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키 (자동 증가)

    @Column(nullable = false, unique = true)
    private String username; // 사용자 이름 (유니크, 필수 입력)

    @Column(nullable = false)
    private String password; // 비밀번호 (필수 입력)

    @Column(nullable = false, unique = true)
    private String email; // 이메일 (유니크, 필수 입력)

    @CreationTimestamp // 엔티티 생성 시 자동으로 현재 시간 입력
    private Timestamp createdAt;

}
