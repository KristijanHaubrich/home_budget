package com.example.home_budget.controller;

import com.example.home_budget.dto.request.CreateDepositRequestDto;
import com.example.home_budget.dto.request.DepositsFilterRequestDto;
import com.example.home_budget.dto.response.CreateDepositResponseDto;
import com.example.home_budget.dto.response.DepositsFilterResponseDto;
import com.example.home_budget.model.Deposit;
import com.example.home_budget.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deposits/")
public class DepositController {
    private final DepositService depositService;
    @GetMapping
    @PreAuthorize("hasAuthority('GET_ALL_DEPOSITS')")
    public List<Deposit> getAllDeposits(){
        return depositService.getAllDeposits();
    }
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('GET_DEPOSIT')")
    public Deposit getExpenseById(@PathVariable Long id){
        return depositService.getDepositById(id);
    }
    @GetMapping("filterDeposits")
    @PreAuthorize("hasAuthority('FILTER_DEPOSITS')")
    public DepositsFilterResponseDto filterDeposits(@RequestBody DepositsFilterRequestDto depositsFilterRequestDto){
        return depositService.filterDeposits(depositsFilterRequestDto);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_DEPOSIT')")
    public CreateDepositResponseDto createDeposit(@RequestBody CreateDepositRequestDto createDepositRequestDto){
        return depositService.createDeposit(createDepositRequestDto);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('DELETE_DEPOSIT')")
    public boolean deleteDeposit(@PathVariable Long id){
        return depositService.deleteDeposit(id);
    }

}
