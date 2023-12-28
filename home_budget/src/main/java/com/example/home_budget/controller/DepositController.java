package com.example.home_budget.controller;

import com.example.home_budget.dto.request.CreateDepositRequestDto;
import com.example.home_budget.dto.request.DepositsFilterRequestDto;
import com.example.home_budget.dto.response.CreateDepositResponseDto;
import com.example.home_budget.dto.response.DepositsFilterResponseDto;
import com.example.home_budget.model.Deposit;
import com.example.home_budget.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deposits/")
public class DepositController {
    private final DepositService depositService;
    @GetMapping
    public List<Deposit> getAllDeposits(){
        return depositService.getAllDeposits();
    }
    @GetMapping("{id}")
    public Deposit getExpenseById(@PathVariable Long id){
        return depositService.getDepositById(id);
    }
    @GetMapping("filterDeposits")
    public DepositsFilterResponseDto filterExpenses(@RequestBody DepositsFilterRequestDto depositsFilterRequestDto){
        return depositService.filterDeposits(depositsFilterRequestDto);
    }
    @PostMapping
    public CreateDepositResponseDto createDeposit(@RequestBody CreateDepositRequestDto createDepositRequestDto){
        return depositService.createDeposit(createDepositRequestDto);
    }
}
