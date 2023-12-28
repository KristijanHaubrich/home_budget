package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {
   Optional<Category> findByName(String name);
}
