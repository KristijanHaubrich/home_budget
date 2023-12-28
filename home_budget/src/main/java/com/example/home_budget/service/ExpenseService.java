package com.example.home_budget.service;

import com.example.home_budget.dto.request.CreateExpenseRequestDto;
import com.example.home_budget.dto.request.ExpensesFilterRequestDto;
import com.example.home_budget.dto.response.CreateExpenseResponseDto;
import com.example.home_budget.dto.response.ExpensesFilterResponseDto;
import com.example.home_budget.filters.ExpenseFilter;
import com.example.home_budget.jpa_repository.CategoryRepo;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.jpa_repository.ExpenseRepo;

import com.example.home_budget.mapper.ExpenseMapper;
import com.example.home_budget.model.Category;
import com.example.home_budget.model.Client;
import com.example.home_budget.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseFilter expenseFilter;
    private final ExpenseRepo expenseRepo;
    private final ClientRepo clientRepo;
    private final CategoryRepo categoryRepo;
    private final ExpenseMapper expenseMapper;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    @Transactional
    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }
    @Transactional
    public Expense getExpenseById(Long id) {
        return expenseRepo.getReferenceById(id);
    }
    @Transactional
    public ExpensesFilterResponseDto filterExpenses(ExpensesFilterRequestDto expensesFilterRequestDto) {
        try{
            List<Expense> expenses = expenseRepo.findAll();

            expenseFilter.filterByCategory(expenses, expensesFilterRequestDto.getCategory());
            expenseFilter.filterByFromDate(expenses, expensesFilterRequestDto.getFromDate());
            expenseFilter.filterByToDate(expenses, expensesFilterRequestDto.getToDate());
            expenseFilter.filterByMinAmount(expenses, expensesFilterRequestDto.getMinAmount());
            expenseFilter.filterByMaxAmount(expenses, expensesFilterRequestDto.getMaxAmount());

            return new ExpensesFilterResponseDto(true, expenses);

        }catch(DateTimeParseException e){
            return new ExpensesFilterResponseDto(false,new ArrayList<>());
        }

    }
    @Transactional
    public CreateExpenseResponseDto createExpense(CreateExpenseRequestDto createExpenseRequestDto) {
        try{
           Optional<Client> clientDB = clientRepo.findByEmail(createExpenseRequestDto.getClientEmail());
           Optional<Category> categoryDB = categoryRepo.findByName(createExpenseRequestDto.getCategoryName());
           boolean clientExist = clientDB.isPresent();
           boolean categoryExist = categoryDB.isPresent();
           boolean isSuccess = false;

           if(clientExist && categoryExist){
               LocalDateTime.parse(createExpenseRequestDto.getDate(),dtf);
               Expense expense = expenseMapper.map(createExpenseRequestDto);
               expense.setClient(clientDB.get());
               expense.setCategory(categoryDB.get());
               Double realAmount = new BigDecimal(expense.getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
               expense.setAmount(realAmount);
               Double newBalance = new BigDecimal(clientDB.get().getBalance() - expense.getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
               clientDB.get().setBalance(newBalance);
               expenseRepo.save(expense);
               isSuccess = true;
           }

           return new CreateExpenseResponseDto(clientExist,categoryExist,true,isSuccess);

        }catch(DateTimeParseException e){
           return new CreateExpenseResponseDto(true,true,false,false);
        }

    }
}
