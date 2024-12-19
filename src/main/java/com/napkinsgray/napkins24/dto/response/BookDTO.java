package com.napkinsgray.napkins24.dto.response;

import com.napkinsgray.napkins24.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stockQuantity;

    // fromEntity 정적 메서드
    public static BookDTO fromEntity(Book book) {
        if(book == null) {
            return null;
        }
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStockQuantity());
    }
}
