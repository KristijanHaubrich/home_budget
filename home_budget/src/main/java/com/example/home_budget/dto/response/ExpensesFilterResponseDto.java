package com.example.home_budget.dto.response;

import com.example.home_budget.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExpensesFilterResponseDto {
    private boolean areDatesValid;
    private List<Expense> expenses;
}
