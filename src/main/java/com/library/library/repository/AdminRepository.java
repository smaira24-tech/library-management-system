package com.library.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsernameAndPassword(String username, String password);

}