package com.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.library.library.model.Book;
import com.library.library.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    // SHOW + SEARCH
    @GetMapping
    public String books(@RequestParam(required = false) String keyword, Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("books", bookRepo.findByTitleContainingIgnoreCase(keyword));
        } else {
            model.addAttribute("books", bookRepo.findAll());
        }

        return "books";
    }

    // ADD
    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        bookRepo.save(book);
        return "redirect:/books";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookRepo.deleteById(id);
        return "redirect:/books";
    }

    // EDIT PAGE
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable int id, Model model) {
        model.addAttribute("book", bookRepo.findById(id).orElse(null));
        return "edit-book";
    }

    // UPDATE
    @PostMapping("/update")
    public String updateBook(@ModelAttribute Book book) {
        bookRepo.save(book);
        return "redirect:/books";
    }
}