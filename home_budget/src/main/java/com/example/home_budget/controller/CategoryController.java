package com.example.home_budget.controller;

import com.example.home_budget.dto.response.CreateCategoryResponseDto;
import com.example.home_budget.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("{name}")
    public CreateCategoryResponseDto createCategory(@PathVariable String name){
        return categoryService.createCategory(name);
    }

}
