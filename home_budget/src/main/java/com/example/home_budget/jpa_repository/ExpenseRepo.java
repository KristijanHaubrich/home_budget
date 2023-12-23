package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepo extends JpaRepository<Expense,Long> {

}
