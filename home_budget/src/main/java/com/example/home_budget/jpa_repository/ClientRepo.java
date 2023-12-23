package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client,Long> {

}
