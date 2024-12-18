package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.BookDTO;
import com.napkinsgray.napkins24.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> books() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

}
