package com.napkinsgray.napkins24.service;

import com.napkinsgray.napkins24.dto.CartDTO;
import com.napkinsgray.napkins24.entity.Book;
import com.napkinsgray.napkins24.entity.Cart;
import com.napkinsgray.napkins24.repository.BookRepository;
import com.napkinsgray.napkins24.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public CartService(CartRepository cartRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    // 모든 장바구니 항목 조회
    public List<CartDTO> getAllCartItems() {
        logger.info("모든 장바구니 항목을 조회합니다...");
        List<CartDTO> cartItems = cartRepository.findAll().stream()
                .map(CartDTO::fromEntity)
                .collect(Collectors.toList());
        logger.info("총 {}개의 장바구니 항목을 조회했습니다.", cartItems.size());
        return cartItems;
    }

    // 장바구니에 책 추가
    public CartDTO addBookToCart(Long bookId, Integer quantity) {
        logger.info("장바구니에 책을 추가합니다. 책 ID: {}, 수량: {}", bookId, quantity);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    logger.error("해당 책을 찾을 수 없습니다. 책 ID: {}", bookId);
                    return new IllegalArgumentException("해당 책을 찾을 수 없습니다. 책 ID: " + bookId);
                });

        if (book.getStockQuantity() < quantity) {
            logger.error("재고가 부족합니다. 책 ID: {}, 현재 재고: {}, 요청 수량: {}", bookId, book.getStockQuantity(), quantity);
            throw new IllegalArgumentException("재고가 부족합니다. 책 ID: " + bookId);
        }

        Cart cart = new Cart();
        cart.setBook(book);
        cart.setQuantity(quantity);
        cartRepository.save(cart);
        logger.info("장바구니에 책이 성공적으로 추가되었습니다. 장바구니 항목 ID: {}", cart.getId());

        // 재고 업데이트
        book.setStockQuantity(book.getStockQuantity() - quantity);
        bookRepository.save(book);
        logger.info("책 재고가 업데이트되었습니다. 책 ID: {}, 남은 재고: {}", bookId, book.getStockQuantity());

        return CartDTO.fromEntity(cart);
    }

    // 장바구니 항목 삭제
    public void removeCartItem(Long cartId) {
        logger.info("장바구니 항목을 삭제합니다. 장바구니 항목 ID: {}", cartId);
        var cartItem = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    logger.error("해당 장바구니 항목을 찾을 수 없습니다. 장바구니 항목 ID: {}", cartId);
                    return new IllegalArgumentException("해당 장바구니 항목을 찾을 수 없습니다. 장바구니 항목 ID: " + cartId);
                });

        var book = cartItem.getBook();

        // 재고 복원
        book.setStockQuantity(book.getStockQuantity() + cartItem.getQuantity());
        bookRepository.save(book);
        logger.info("책 재고가 복원되었습니다. 책 ID: {}, 복원된 수량: {}", book.getId(), cartItem.getQuantity());

        // 장바구니 항목 삭제
        cartRepository.delete(cartItem);
        logger.info("장바구니 항목이 성공적으로 삭제되었습니다. 장바구니 항목 ID: {}", cartId);
    }
}
