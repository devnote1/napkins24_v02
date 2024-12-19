package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.response.CartDTO;
import com.napkinsgray.napkins24.service.CartService;
import com.napkinsgray.napkins24.utils.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart", description = "장바구니 관련 API")
@RestController
@RequestMapping("/carts")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 장바구니 조회
     *
     * @param request 사용자 요청 정보 (userId 포함)
     * @return 공통 DTO(ApiUtil) 형식의 장바구니 목록
     */
    @Operation(summary = "장바구니 조회", description = "사용자의 모든 장바구니 항목을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiUtil<List<CartDTO>>> carts(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<CartDTO> cartItems = cartService.getAllCartItems(userId);
        return ResponseEntity.ok(new ApiUtil<>(cartItems));
    }

    /**
     * 장바구니에 책 추가
     *
     * @param request 사용자 요청 정보 (userId 포함)
     * @param cartDTO 장바구니에 추가할 책 정보
     * @return 공통 DTO(ApiUtil) 형식의 추가된 장바구니 항목
     */
    @Operation(summary = "장바구니에 책 추가", description = "장바구니에 책을 추가합니다.")
    @PostMapping("/items")
    public ResponseEntity<ApiUtil<CartDTO>> addBookToCart(HttpServletRequest request, @RequestBody CartDTO cartDTO) {
        Long userId = (Long) request.getAttribute("userId");
        CartDTO savedCartDTO = cartService.addBookToCart(userId, cartDTO.getBookId(), cartDTO.getQuantity());
        return ResponseEntity.ok(new ApiUtil<>(savedCartDTO));
    }

    /**
     * 장바구니 항목 삭제
     *
     * @param request 사용자 요청 정보 (userId 포함)
     * @param id 삭제할 장바구니 항목 ID
     * @return 공통 DTO(ApiUtil) 형식의 응답 (body 없음)
     */
    @Operation(summary = "장바구니 항목 삭제", description = "장바구니에서 특정 항목을 삭제합니다.")
    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiUtil<Void>> removeCartItem(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.removeCartItem(userId, id);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
