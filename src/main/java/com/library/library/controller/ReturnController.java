package com.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.library.library.repository.ReturnRepository;
import com.library.library.service.LibraryService;

@Controller
@RequestMapping("/return")
public class ReturnController {

    @Autowired
    private LibraryService service;

    @Autowired
    private ReturnRepository returnRepo;

    @GetMapping
    public String returnPage(Model model) {
        model.addAttribute("returns", returnRepo.findAll());
        return "return";
    }

    @PostMapping
    public String returnBook(@RequestParam int issueId, Model model) {

        double fine = service.returnBook(issueId);

        if (fine == -1) {
            model.addAttribute("msg", "Invalid Issue ID");
        } else if (fine == -2) {
            model.addAttribute("msg", "Already Returned");
        } else {
            model.addAttribute("msg", "Returned Successfully");
            model.addAttribute("fine", "Fine: ₹" + fine);
        }

        model.addAttribute("returns", returnRepo.findAll());

        return "return";
    }
}