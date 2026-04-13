package com.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "issue_table")   // avoid SQL keyword conflict
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int issueId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String issueDate;
    private String returnDate;
    private String status;

    private int fine;   // ⭐ new field

    // Getters & Setters

    public int getIssueId() { return issueId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getFine() { return fine; }
    public void setFine(int fine) { this.fine = fine; }
}