package com.napkinsgray.napkins24.dto;

import com.napkinsgray.napkins24.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private Long id;
    private Long bookId;
    private Integer quantity;
    private String bookTitle;
    private String bookAuthor;
    private BigDecimal bookPrice;




    // fromEntity 정적 메서드
    public static CartDTO fromEntity(Cart cart) {
        if(cart == null) {
            return  null;
        }
        return new CartDTO(
                cart.getId(),
                cart.getBook().getId(),
                cart.getQuantity(),
                cart.getBook().getTitle(),
                cart.getBook().getAuthor(),
                cart.getBook().getPrice()
        );
    }

}
