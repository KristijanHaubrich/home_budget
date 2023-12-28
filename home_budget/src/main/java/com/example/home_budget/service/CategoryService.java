package com.example.home_budget.service;

import com.example.home_budget.dto.response.CreateCategoryResponseDto;
import com.example.home_budget.jpa_repository.CategoryRepo;
import com.example.home_budget.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    @Transactional
    public CreateCategoryResponseDto createCategory(String name) {
        Optional<Category> categoryDB = categoryRepo.findByName(name);
        boolean categoryExist = categoryDB.isPresent();
        boolean isValidName = !name.equals("-1");
        boolean isSuccess = false;
        if(!categoryExist && isValidName){
            Category category = new Category();
            category.setName(name);
            categoryRepo.save(category);
            isSuccess = true;
        }
        return new CreateCategoryResponseDto(categoryExist,isValidName,isSuccess);
    }
    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }
    @Transactional
    public Category getCategoryById(Long id) {
        Optional<Category> categoryDB = categoryRepo.findById(id);
        if(categoryDB.isPresent()) return categoryDB.get();
        return null;
    }
    @Transactional
    public Boolean deleteCategory(Long id) {
        Optional<Category> categoryDB = categoryRepo.findById(id);
        boolean success = false;
        if(categoryDB.isPresent()) {
            categoryRepo.delete(categoryDB.get());
            success = true;
        }
        return success;
    }
}
