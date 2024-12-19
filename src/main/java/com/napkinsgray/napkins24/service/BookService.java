package com.napkinsgray.napkins24.service;

import com.napkinsgray.napkins24.dto.BookDTO;
import com.napkinsgray.napkins24.entity.Book;
import com.napkinsgray.napkins24.exceptions.errors.Exception404;
import com.napkinsgray.napkins24.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * 모든 책 조회
     *
     * @return 책 목록
     */
    public List<BookDTO> getAllBooks() {
        List<BookDTO> books = bookRepository.findAll().stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getStockQuantity()))
                .collect(Collectors.toList());
        logger.info("총 {}권의 책을 조회했습니다.", books.size());
        return books;
    }

    /**
     * 책 ID로 단일 책 조회
     *
     * @param id 책 ID
     * @return 조회된 책 DTO
     */
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("책을 찾을 수 없습니다. 책 ID: {}", id);
                    return new Exception404("책 ID: " + id + " 책을 찾을 수 없습니다.");
                });
        logger.info("책을 조회했습니다. 책 ID: {}", id);
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getStockQuantity());
    }

    /**
     * 책 추가
     *
     * @param bookDTO 책 DTO
     * @return 추가된 책 DTO
     */
    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .price(bookDTO.getPrice())
                .stockQuantity(bookDTO.getStockQuantity())
                .build();

        Book savedBook = bookRepository.save(book);
        logger.info("새로운 책이 추가되었습니다. 책 ID: {}", savedBook.getId());
        return new BookDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getAuthor(),
                savedBook.getPrice(),
                savedBook.getStockQuantity()
        );
    }

    /**
     * 책 삭제
     *
     * @param id 책 ID
     */
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("삭제할 책을 찾을 수 없습니다. 책 ID: {}", id);
                    return new Exception404("책 ID: " + id + " 삭제할 책을 찾을 수 없습니다.");
                });

        bookRepository.delete(book);
        logger.info("책이 삭제되었습니다. 책 ID: {}", id);
    }
}
