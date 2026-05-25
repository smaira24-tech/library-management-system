package com.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.library.library.repository.IssueRepository;
import com.library.library.service.LibraryService;

@Controller
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private LibraryService service;

    @Autowired
    private IssueRepository issueRepo;

    // SHOW PAGE + TABLE
    @GetMapping
    public String issuePage(Model model) {
        model.addAttribute("issues", issueRepo.findAll());
        return "issue";
    }

    // ISSUE BOOK
    @PostMapping
    public String issue(@RequestParam int studentId,
                        @RequestParam int bookId,
                        @RequestParam String issueDate,
                        @RequestParam String dueDate,
                        Model model) {

        String msg = service.issueBook(studentId, bookId, issueDate, dueDate);

        model.addAttribute("msg", msg);
        model.addAttribute("issues", issueRepo.findAll());

        return "issue";
    }
}