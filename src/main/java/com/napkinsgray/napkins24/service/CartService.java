package com.napkinsgray.napkins24.service;

import com.napkinsgray.napkins24.dto.CartDTO;
import com.napkinsgray.napkins24.entity.Book;
import com.napkinsgray.napkins24.entity.Cart;
import com.napkinsgray.napkins24.entity.User;
import com.napkinsgray.napkins24.exceptions.errors.Exception400;
import com.napkinsgray.napkins24.exceptions.errors.Exception401;
import com.napkinsgray.napkins24.repository.BookRepository;
import com.napkinsgray.napkins24.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 사용자별 모든 장바구니 항목 조회
     *
     * @param userId 사용자 ID
     * @return 장바구니 항목 리스트
     */
    public List<CartDTO> getAllCartItems(Long userId) {
        List<CartDTO> cartItems = cartRepository.findCartsByUserId(userId).stream()
                .map(CartDTO::fromEntity) // 메소드 참조 문법
                .collect(Collectors.toList());
        logger.info("총 {}개의 장바구니 항목을 조회했습니다.", cartItems.size());
        return cartItems;
    }

    /**
     * 장바구니에 책 추가
     *
     * @param userId   사용자 ID
     * @param bookId   책 ID
     * @param quantity 수량
     * @return 추가된 장바구니 항목 DTO
     */
    @Transactional
    public CartDTO addBookToCart(Long userId, Long bookId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new Exception400("책 ID: " + bookId + " 해당 책을 찾을 수 없습니다."));

        if (book.getStockQuantity() < quantity) {
            throw new Exception400("책 ID: " + bookId + " 재고가 부족합니다.");
        }

        // 장바구니 추가
        Cart cart = Cart.builder()
                .user(new User(userId))
                .book(book)
                .quantity(quantity)
                .build();
        cartRepository.save(cart);

        // 책 재고 업데이트
        book.setStockQuantity(book.getStockQuantity() - quantity);
        bookRepository.save(book);

        logger.info("장바구니에 책이 추가되었습니다. 책 ID: {}, 남은 재고: {}", bookId, book.getStockQuantity());
        return CartDTO.fromEntity(cart);
    }

    /**
     * 장바구니 항목 삭제
     *
     * @param userId 사용자 ID
     * @param cartId 장바구니 항목 ID
     */
    @Transactional
    public void removeCartItem(Long userId, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new Exception400("장바구니 ID: " + cartId + " 장바구니 항목을 찾을 수 없습니다.")
                );

        // 권한 확인
        if (!cart.getUser().getId().equals(userId)) {
            logger.error("사용자 ID가 일치하지 않습니다. 요청 사용자 ID: {}, 실제 사용자 ID: {}", userId, cart.getUser().getId());
            throw new Exception401("권한이 없습니다.");
        }

        // 책 재고 복구
        Book book = cart.getBook();
        book.setStockQuantity(book.getStockQuantity() + cart.getQuantity());
        bookRepository.save(book);

        // 장바구니 항목 삭제
        cartRepository.delete(cart);
        logger.info("장바구니 항목이 삭제되었습니다. 장바구니 ID: {}", cartId);
    }
}
