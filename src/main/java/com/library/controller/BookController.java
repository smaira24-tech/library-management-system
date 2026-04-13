package com.library.controller;

import com.library.model.*;
import com.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private IssueRepository issueRepo;

    // HOME
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookRepo.findAll());
        model.addAttribute("students", studentRepo.findAll());
        model.addAttribute("issues", issueRepo.findAll());
        return "index";
    }

    // ADD BOOK
    @PostMapping("/addBook")
    public String addBook(Book book) {
        bookRepo.save(book);
        return "redirect:/";
    }

    // ADD STUDENT
    @PostMapping("/addStudent")
    public String addStudent(Student student) {
        studentRepo.save(student);
        return "redirect:/";
    }

    // ISSUE BOOK
    @PostMapping("/issueBook")
    public String issueBook(@RequestParam int studentId,
                            @RequestParam int bookId,
                            @RequestParam String issueDate,
                            @RequestParam String returnDate) {

        Student student = studentRepo.findById(studentId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);

        if (student == null || book == null) {
            return "redirect:/";
        }

        Issue issue = new Issue();
        issue.setStudent(student);
        issue.setBook(book);
        issue.setIssueDate(issueDate);
        issue.setReturnDate(returnDate);
        issue.setStatus("ISSUED");
        issue.setFine(0);

        issueRepo.save(issue);

        return "redirect:/";
    }

    // RETURN BOOK + FINE
    @PostMapping("/returnBook")
    public String returnBook(@RequestParam int issueId) {

        Issue issue = issueRepo.findById(issueId).orElse(null);

        if (issue == null) return "redirect:/";

        issue.setStatus("RETURNED");

        try {
            java.time.LocalDate returnDate = java.time.LocalDate.parse(issue.getReturnDate());
            java.time.LocalDate today = java.time.LocalDate.now();

            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(returnDate, today);

            if (daysLate > 0) {
                issue.setFine((int) daysLate * 5);
            } else {
                issue.setFine(0);
            }

        } catch (Exception e) {
            issue.setFine(0);
        }

        issueRepo.save(issue);

        return "redirect:/";
    }

    // DELETE BOOK
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable int id) {

        boolean isUsed = issueRepo.findAll().stream()
                .anyMatch(i -> i.getBook().getBookId() == id);

        if (isUsed) {
            System.out.println("❌ Book is currently issued, cannot delete");
            return "redirect:/";
        }

        bookRepo.deleteById(id);

        return "redirect:/";
    }

    // DELETE STUDENT
    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentRepo.deleteById(id);
        return "redirect:/";
    }

    // EDIT BOOK
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable int id, Model model) {
        Book book = bookRepo.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "editBook";
    }

    // UPDATE BOOK
    @PostMapping("/updateBook")
    public String updateBook(Book book) {
        bookRepo.save(book);
        return "redirect:/";
    }
}