package com.napkinsgray.napkins24.service;

import com.napkinsgray.napkins24.dto.BookDTO;
import com.napkinsgray.napkins24.entity.Book;
import com.napkinsgray.napkins24.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 모든 책 조회
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getStockQuantity()))
                .collect(Collectors.toList());
    }

    // 책 ID로 단일 책 조회
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getStockQuantity());
    }

}
