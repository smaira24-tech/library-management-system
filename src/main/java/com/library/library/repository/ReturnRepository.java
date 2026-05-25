package com.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library.model.ReturnBook;

public interface ReturnRepository extends JpaRepository<ReturnBook, Integer> {
}