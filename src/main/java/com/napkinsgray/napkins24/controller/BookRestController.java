package com.napkinsgray.napkins24.controller;

import com.napkinsgray.napkins24.dto.response.BookDTO;
import com.napkinsgray.napkins24.service.BookService;
import com.napkinsgray.napkins24.utils.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Book", description = "책 관련 API")
@RestController
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 책 목록 조회 API
     *
     * @return 공통 DTO(ApiUtil) 형식의 책 목록
     */
    @Operation(summary = "책 목록 조회", description = "등록된 모든 책의 정보를 조회합니다.")
    @GetMapping("/books")
    public ResponseEntity<ApiUtil<List<BookDTO>>> books() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(new ApiUtil<>(books));
    }
}
