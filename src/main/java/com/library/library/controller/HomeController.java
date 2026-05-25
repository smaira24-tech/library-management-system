package com.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.library.library.repository.*;

@Controller
public class HomeController {

    @Autowired private BookRepository bookRepo;
    @Autowired private StudentRepository studentRepo;
    @Autowired private IssueRepository issueRepo;
    @Autowired private ReturnRepository returnRepo;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalBooks", bookRepo.count());
        model.addAttribute("totalStudents", studentRepo.count());
        model.addAttribute("issuedBooks", issueRepo.count());
        model.addAttribute("returnedBooks", returnRepo.count());

        return "index";
    }
}