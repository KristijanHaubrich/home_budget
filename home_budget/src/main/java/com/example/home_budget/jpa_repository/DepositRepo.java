package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepo extends JpaRepository<Deposit,Long> {

}