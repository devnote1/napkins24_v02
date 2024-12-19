package com.napkinsgray.napkins24.repository;

import com.napkinsgray.napkins24.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 특정 사용자(User ID)의 장바구니 항목 조회
    List<Cart> findByUserId(Long userId);

    // JPQL로 작성된 동일한 기능
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    List<Cart> findCartsByUserId(@Param("userId") Long userId);
}
