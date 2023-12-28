package com.example.home_budget.mapper;

import com.example.home_budget.dto.request.CreateExpenseRequestDto;
import com.example.home_budget.model.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    Expense map(CreateExpenseRequestDto createExpenseRequestDto);
}
