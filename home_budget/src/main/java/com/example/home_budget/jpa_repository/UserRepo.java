package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
}
