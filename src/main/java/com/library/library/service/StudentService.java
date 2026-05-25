package com.library.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.library.library.model.Student;
import com.library.library.repository.StudentRepository;
import java.util.List;

@Service
public class StudentService {

    @Autowired private StudentRepository repo;

    public List<Student> getAll() {
        return repo.findAll();
    }

    public void save(Student s) {
        repo.save(s);
    }
}