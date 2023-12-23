package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

}
