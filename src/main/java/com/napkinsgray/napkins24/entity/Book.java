package com.napkinsgray.napkins24.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(precision = 10, scale = 2) // 최대 10자리, 소수점 이하 2자리
    private BigDecimal price;
    private Integer stockQuantity;

    // 샘플 데이터 입력을 위한 생성자
    public Book(String title, String author, BigDecimal price, Integer stockQuantity) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
