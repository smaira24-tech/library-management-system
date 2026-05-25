package com.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.library.library.model.Book;
import com.library.library.model.Issue;
import com.library.library.model.ReturnBook;
import com.library.library.model.Student;
import com.library.library.repository.BookRepository;
import com.library.library.repository.IssueRepository;
import com.library.library.repository.ReturnRepository;
import com.library.library.repository.StudentRepository;

@Service
public class LibraryService {

    @Autowired private BookRepository bookRepo;
    @Autowired private StudentRepository studentRepo;
    @Autowired private IssueRepository issueRepo;
    @Autowired private ReturnRepository returnRepo;

    // ================= ISSUE BOOK =================
    @Transactional
    public String issueBook(int studentId, int bookId,
                            String issueDateStr, String dueDateStr) {

        Book book = bookRepo.findById(bookId).orElse(null);
        Student student = studentRepo.findById(studentId).orElse(null);

        if (book == null || student == null)
            return "Invalid Student or Book ID";

        if (book.getQuantity() <= 0)
            return "Book not available";

        LocalDate issueDate = LocalDate.parse(issueDateStr);
        LocalDate dueDate = LocalDate.parse(dueDateStr);

        if (dueDate.isBefore(issueDate))
            return "Due date must be after issue date";

        // 🔽 Decrease quantity
        book.setQuantity(book.getQuantity() - 1);

        Issue issue = new Issue();
        issue.setBook(book);
        issue.setStudent(student);
        issue.setIssueDate(issueDate);
        issue.setReturnDate(dueDate); // storing due date
        issue.setStatus("ISSUED");

        bookRepo.save(book);
        issueRepo.save(issue);

        return "Book Issued Successfully";
    }

    // ================= RETURN BOOK =================
    @Transactional
    public double returnBook(int issueId) {

        Issue issue = issueRepo.findById(issueId).orElse(null);

        if (issue == null)
            return -1; // invalid

        if ("RETURNED".equals(issue.getStatus()))
            return -2; // already returned

        LocalDate today = LocalDate.now();
        LocalDate dueDate = issue.getReturnDate();

        // 🔥 Calculate delay
        long lateDays = ChronoUnit.DAYS.between(dueDate, today);
        if (lateDays < 0) lateDays = 0;

        double fine = lateDays * 5;

        // 🔥 Update status
        issue.setStatus("RETURNED");

        // 🔥 Update book quantity (IMPORTANT FIX)
        Book book = issue.getBook();
        if (book != null) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepo.save(book);
        }

        // 🔥 Save into returns table
        ReturnBook rb = new ReturnBook();
        rb.setIssue(issue);
        rb.setReturnDate(today);
        rb.setFine(fine);

        issueRepo.save(issue);
        returnRepo.save(rb);

        return fine;
    }
}