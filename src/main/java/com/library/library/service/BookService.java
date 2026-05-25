package com.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.library.model.Book;
import com.library.library.repository.BookRepository;
import java.util.List;

@Service
public class BookService {

    @Autowired private BookRepository repo;

    public List<Book> getAll() {
        return repo.findAll();
    }

    public void save(Book book) {
        repo.save(book);
    }
}