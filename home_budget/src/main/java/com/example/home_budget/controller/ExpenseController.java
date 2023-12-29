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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses/")
public class ExpenseController {
    private final ExpenseService expenseService;
    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALL_EXPENSES')")
    public List<Expense> getAllExpenses(){
        return expenseService.getAllExpenses();
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('GET_EXPENSE')")
    public Expense getExpenseById(@PathVariable Long id){
        return expenseService.getExpenseById(id);
    }
    @GetMapping("filterExpenses")
    @PreAuthorize("hasAuthority('FILTER_EXPENSES')")
    public ExpensesFilterResponseDto filterExpenses(@RequestBody ExpensesFilterRequestDto expensesFilterRequestDto){
        return expenseService.filterExpenses(expensesFilterRequestDto);
    }
    @PatchMapping("editDescription/{id}/{description}")
    @PreAuthorize("hasAuthority('EDIT_EXPENSE_DESCRIPTION')")
    public boolean editDescription(@PathVariable Long id,@PathVariable String description){
        return expenseService.editDescription(id,description);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_EXPENSE')")
    public CreateExpenseResponseDto createExpense(@RequestBody CreateExpenseRequestDto createExpenseRequestDto){
        return expenseService.createExpense(createExpenseRequestDto);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('DELETE_EXPENSE')")
    public boolean deleteExpense(@PathVariable Long id){
        return expenseService.deleteExpense(id);
    }
}
