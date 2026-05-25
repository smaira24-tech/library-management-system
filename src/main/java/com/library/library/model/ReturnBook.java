package com.library.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "returns")
public class ReturnBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int returnId;

    @OneToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    private LocalDate returnDate;

    private double fine;

    // ✅ GETTERS & SETTERS

    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}