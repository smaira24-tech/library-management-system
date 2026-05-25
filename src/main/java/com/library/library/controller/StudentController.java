package com.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.library.library.model.Student;
import com.library.library.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    // ================= SHOW + SEARCH =================
    @GetMapping
    public String students(@RequestParam(required = false) String keyword, Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("students", studentRepo.findByNameContainingIgnoreCase(keyword));
        } else {
            model.addAttribute("students", studentRepo.findAll());
        }

        model.addAttribute("total", studentRepo.count());

        return "students";
    }

    // ================= ADD =================
    @PostMapping
    public String addStudent(@ModelAttribute Student student) {
        studentRepo.save(student);
        return "redirect:/students";
    }

    // ================= DELETE =================
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentRepo.deleteById(id);
        return "redirect:/students";
    }

    // ================= EDIT PAGE =================
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable int id, Model model) {
        model.addAttribute("student", studentRepo.findById(id).orElse(null));
        return "edit-student";
    }

    // ================= UPDATE =================
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {
        studentRepo.save(student);
        return "redirect:/students";
    }
}