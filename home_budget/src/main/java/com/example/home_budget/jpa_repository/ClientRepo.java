package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Client;
import com.example.home_budget.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client,Long> {
    Optional<Client> findByEmail(String email);
}
