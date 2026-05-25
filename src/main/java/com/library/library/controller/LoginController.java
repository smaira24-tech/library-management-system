package com.library.library.controller;

import com.library.library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private AdminRepository repo;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {

        if (repo.findByUsernameAndPassword(username, password) != null)
            return "redirect:/";

        model.addAttribute("error", "Invalid credentials");
        return "login";
    }


}