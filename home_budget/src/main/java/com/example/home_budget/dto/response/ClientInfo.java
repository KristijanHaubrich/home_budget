package com.example.home_budget.dto.response;

import com.example.home_budget.model.Deposit;
import com.example.home_budget.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClientInfo {
    private String email;
    private String name;
    private List<Expense> expenses;
    private Double balance;

    private List<Deposit> deposits;
}
