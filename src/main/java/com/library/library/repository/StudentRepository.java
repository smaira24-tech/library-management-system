package com.library.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // 🔍 Search by name
    List<Student> findByNameContainingIgnoreCase(String keyword);
}