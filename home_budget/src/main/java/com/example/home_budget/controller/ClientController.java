package com.example.home_budget.controller;

import com.example.home_budget.dto.request.ChangeClientPassRequestDto;
import com.example.home_budget.dto.request.ClientProfitRequestDto;
import com.example.home_budget.dto.request.FilterClientDepositsRequestDto;
import com.example.home_budget.dto.request.FilterClientExpensesRequestDto;
import com.example.home_budget.dto.response.*;
import com.example.home_budget.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients/")
public class ClientController {
    private final ClientService clientService;
    @GetMapping("getAccessToken/{email}")
    @PreAuthorize("hasAuthority('GET_CLIENT_ACCESS_TOKEN')")
    public GetAccessTokenResponseDto getAccessToken(@PathVariable String email){
        return clientService.getAccessToken(email);
    }
    @GetMapping("getClientInfo/{email}")
    @PreAuthorize("hasAuthority('GET_CLIENT_INFO')")
    public ClientInfo getClientInfo(@PathVariable String email){
        return clientService.getClientInfo(email);
    }
    @GetMapping("filterClientDeposits")
    @PreAuthorize("hasAuthority('FILTER_CLIENT_DEPOSITS')")
    public FilterClientDepositsResponseDto filterClientDeposits(@RequestBody FilterClientDepositsRequestDto filterClientDepositsRequestDto){
        return clientService.filterClientDeposits(filterClientDepositsRequestDto);
    }
    @GetMapping("filterClientExpenses")
    @PreAuthorize("hasAuthority('FILTER_CLIENT_EXPENSES')")
    public FilterClientExpensesResponseDto filterClientExpenses(@RequestBody FilterClientExpensesRequestDto filterClientExpensesRequestDto){
        return clientService.filterClientExpenses(filterClientExpensesRequestDto);
    }
    @GetMapping("clientProfit")
    @PreAuthorize("hasAuthority('CLIENT_PROFIT')")
    public ClientProfitResponseDto clientProfit(@RequestBody ClientProfitRequestDto clientProfitRequestDto){
        return clientService.clientProfit(clientProfitRequestDto);
    }
    @PatchMapping("changePass")
    @PreAuthorize("hasAuthority('CHANGE_CLIENT_PASS')")
    public ClientChangePassResponseDto changePass(@RequestBody ChangeClientPassRequestDto changeClientPassRequestDto){
        return clientService.changePass(changeClientPassRequestDto);
    }
    @DeleteMapping("{email}")
    @PreAuthorize("hasAuthority('DELETE_CLIENT')")
    public Boolean deleteClient(@PathVariable String email){
        return clientService.deleteClient(email);
    }
}
