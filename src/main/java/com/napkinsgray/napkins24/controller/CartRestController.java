package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.CartDTO;
import com.napkinsgray.napkins24.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> carts(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(cartService.getAllCartItems(userId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addBookToCart(HttpServletRequest request, @RequestBody CartDTO cartDTO) {
        Long userId = (Long) request.getAttribute("userId");
        CartDTO savedCartDTO =  cartService.addBookToCart(userId, cartDTO.getBookId(), cartDTO.getQuantity());
        return ResponseEntity.ok(savedCartDTO);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeCartItem(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.removeCartItem(userId, id);
        return ResponseEntity.noContent().build();
    }

}
