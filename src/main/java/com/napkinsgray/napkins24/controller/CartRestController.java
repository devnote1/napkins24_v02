package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.CartDTO;
import com.napkinsgray.napkins24.service.CartService;
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
    public ResponseEntity<List<CartDTO>> carts() {
        return ResponseEntity.ok(cartService.getAllCartItems());
    }

    @PostMapping("/items")
    public ResponseEntity<CartDTO> addBookToCart(@RequestBody CartDTO cartDTO) {
        CartDTO savedCartDTO =  cartService.addBookToCart(cartDTO.getBookId(), cartDTO.getQuantity());
        return ResponseEntity.ok(savedCartDTO);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }

}
