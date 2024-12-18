package com.napkinsgray.napkins24.repository;

import com.napkinsgray.napkins24.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 기본으로 제공되지 않는 기능 추가(추상 메서드)

    // 쿼리 메서드 사용 (메서드 이름이 JPA 쿼리로 변환)
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // JPQL을 사용하여 사용자 조회
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsernameJPQL(@Param("username") String username);

    // JPQL을 사용하여 존재 여부 확인
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username")
    boolean checkUserExistsByUsernameJPQL(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean checkUserExistsByEmailJPQL(@Param("email") String email);

    // 네이티브 쿼리는 직접 만들어 보세요
}
