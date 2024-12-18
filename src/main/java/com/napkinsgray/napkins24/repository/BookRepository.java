package com.napkinsgray.napkins24.repository;

import com.napkinsgray.napkins24.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
