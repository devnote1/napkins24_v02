package com.napkinsgray.napkins24.utils;

import com.napkinsgray.napkins24.entity.Book;
import com.napkinsgray.napkins24.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {


    private final BookRepository bookRepository;

    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bookRepository.save(new Book("자바의 정석", "남궁성", BigDecimal.valueOf(25000.00), 10));
        bookRepository.save(new Book("스프링 부트 실전", "김영한", BigDecimal.valueOf(35000), 7));
        bookRepository.save(new Book("리액트 완벽 가이드", "이호진", BigDecimal.valueOf(30000), 5));
    }
}
