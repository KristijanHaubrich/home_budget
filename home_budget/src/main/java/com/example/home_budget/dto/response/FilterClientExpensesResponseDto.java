package com.example.home_budget.dto.response;

import com.example.home_budget.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilterClientExpensesResponseDto {
    private boolean areDatesValid;
    private boolean clientExist;
    private List<Expense> expenses;
}
