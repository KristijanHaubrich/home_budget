package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Permission;
import com.example.home_budget.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permission,Long> {
    Optional<Role> findByName(String name);
}
