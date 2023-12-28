package com.example.home_budget.controller;

import com.example.home_budget.dto.response.CreateCategoryResponseDto;
import com.example.home_budget.model.Category;
import com.example.home_budget.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALL_CATEGORIES')")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('GET_CATEGORY')")
    public Category getCategoryById(@PathVariable  Long id){
        return categoryService.getCategoryById(id);
    }
    @PostMapping("{name}")
    @PreAuthorize("hasAuthority('CREATE_CATEGORY')")
    public CreateCategoryResponseDto createCategory(@PathVariable String name){
        return categoryService.createCategory(name);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    public Boolean deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
