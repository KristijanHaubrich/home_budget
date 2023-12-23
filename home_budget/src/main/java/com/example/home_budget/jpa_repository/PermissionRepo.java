package com.example.home_budget.jpa_repository;

import com.example.home_budget.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission,Long> {

}
