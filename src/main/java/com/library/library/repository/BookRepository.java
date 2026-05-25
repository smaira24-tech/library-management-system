package com.library.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContainingIgnoreCase(String keyword);
}