package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.SuperUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperUserRepo extends JpaRepository<SuperUser,Long> {

}
