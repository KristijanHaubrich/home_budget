package com.example.home_budget.controller;

import com.example.home_budget.dto.request.CreateDepositRequestDto;
import com.example.home_budget.dto.request.CreateExpenseRequestDto;
import com.example.home_budget.dto.request.ExpensesFilterRequestDto;
import com.example.home_budget.dto.response.CreateDepositResponseDto;
import com.example.home_budget.dto.response.CreateExpenseResponseDto;
import com.example.home_budget.dto.response.ExpensesFilterResponseDto;
import com.example.home_budget.model.Expense;
import com.example.home_budget.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses/")
public class ExpenseController {
    private final ExpenseService expenseService;
    @GetMapping
    public List<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }
    @GetMapping("{id}")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }
    @GetMapping("filterExpenses")
    public ExpensesFilterResponseDto filterExpenses(@RequestBody ExpensesFilterRequestDto expensesFilterRequestDto){
        return expenseService.filterExpenses(expensesFilterRequestDto);
    }
    @PostMapping
    public CreateExpenseResponseDto createDeposit(@RequestBody CreateExpenseRequestDto createExpenseRequestDto){
        return expenseService.createExpense(createExpenseRequestDto);
    }
}
