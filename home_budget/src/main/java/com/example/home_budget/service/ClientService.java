package com.example.home_budget.service;

import com.example.home_budget.auth.JwtService;
import com.example.home_budget.dto.request.ChangeClientPassRequestDto;
import com.example.home_budget.dto.request.ClientProfitRequestDto;
import com.example.home_budget.dto.request.FilterClientDepositsRequestDto;
import com.example.home_budget.dto.request.FilterClientExpensesRequestDto;
import com.example.home_budget.dto.response.*;
import com.example.home_budget.filters.DepositFilter;
import com.example.home_budget.filters.ExpenseFilter;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.mapper.ClientMapper;
import com.example.home_budget.model.Client;
import com.example.home_budget.model.Deposit;
import com.example.home_budget.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeParseException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final DepositFilter depositFilter;
    private final ExpenseFilter expenseFilter;
    private final JwtService jwtService;
    private final ClientMapper clientMapper;
    private static final int accessTokenDuration = 1000*60*60*3; //3 sata
    @Transactional
    public ClientChangePassResponseDto changePass(ChangeClientPassRequestDto changeClientPassRequestDto) {
        Optional<Client> clientDB = clientRepo.findByEmail(changeClientPassRequestDto.getEmail());
        boolean clientExist = clientDB.isPresent();
        boolean correctOldPass = false;
        boolean isSuccessfull = false;
        if(clientExist){
            correctOldPass = passwordEncoder.matches(changeClientPassRequestDto.getOldPass(),clientDB.get().getPassword());
            if(correctOldPass){
                String newPassword = passwordEncoder.encode(changeClientPassRequestDto.getNewPass());
                clientDB.get().setPassword(newPassword);
                isSuccessfull = true;
            }
        }

        return new ClientChangePassResponseDto(isSuccessfull,clientExist,correctOldPass);
    }
    @Transactional
    public GetAccessTokenResponseDto getAccessToken(String email) {
        Optional<Client> clientDB = clientRepo.findByEmail(email);
        String accessToken = "null";

        if(clientDB.isPresent()){
            Map<String, Object> authorities = new HashMap<>();
            List<String> permissions = new ArrayList<>();

            clientDB.get().getAuthorities().forEach(
                    grantedAuthority -> permissions.add(grantedAuthority.getAuthority())
            );

            authorities.put("authorities", permissions);

            accessToken = jwtService.generateToken(authorities, clientDB.get(), accessTokenDuration);
        }

        return new GetAccessTokenResponseDto(accessToken,clientDB.isPresent());
    }
    @Transactional
    public FilterClientDepositsResponseDto filterClientDeposits(FilterClientDepositsRequestDto filterClientDepositsRequestDto) {
        try{
            Optional<Client> clientDB = clientRepo.findByEmail(filterClientDepositsRequestDto.getClientEmail());
            if(clientDB.isPresent()){
                List<Deposit> deposits = clientDB.get().getDeposits();
                depositFilter.filterByFromDate(deposits,filterClientDepositsRequestDto.getFromDate());
                depositFilter.filterByToDate(deposits,filterClientDepositsRequestDto.getToDate());
                depositFilter.filterByMinAmount(deposits,filterClientDepositsRequestDto.getMinAmount());
                depositFilter.filterByMaxAmount(deposits,filterClientDepositsRequestDto.getMaxAmount());
                return new FilterClientDepositsResponseDto(true,true,deposits);
            }
            return new FilterClientDepositsResponseDto(false,true,new ArrayList<>());
        }catch(DateTimeParseException e){
            return new FilterClientDepositsResponseDto(true,false,new ArrayList<>());
        }
    }
    @Transactional
    public FilterClientExpensesResponseDto filterClientExpenses(FilterClientExpensesRequestDto filterClientExpensesRequestDto) {
        try{
            Optional<Client> clientDB = clientRepo.findByEmail(filterClientExpensesRequestDto.getClientEmail());
            if(clientDB.isPresent()){
                List<Expense> expenses = clientDB.get().getExpenses();

                expenseFilter.filterByCategory(expenses, filterClientExpensesRequestDto.getCategory());
                expenseFilter.filterByFromDate(expenses, filterClientExpensesRequestDto.getFromDate());
                expenseFilter.filterByToDate(expenses, filterClientExpensesRequestDto.getToDate());
                expenseFilter.filterByMinAmount(expenses, filterClientExpensesRequestDto.getMinAmount());
                expenseFilter.filterByMaxAmount(expenses, filterClientExpensesRequestDto.getMaxAmount());
                return new FilterClientExpensesResponseDto(true,true,expenses.stream().toList());
            }
            return new FilterClientExpensesResponseDto(true,false,new ArrayList<>());
        }catch(DateTimeParseException e){
            return new FilterClientExpensesResponseDto(false,true,new ArrayList<>());
        }
    }
    @Transactional
    public ClientProfitResponseDto clientProfit(ClientProfitRequestDto clientProfitRequestDto) {
        Optional<Client> clientDB = clientRepo.findByEmail(clientProfitRequestDto.getClientEmail());
        boolean clientExist = clientDB.isPresent();
        Double totalDeposit = 0.00;
        Double totalExpense = 0.00;
        Double profit = 0.00;
        if(clientExist) {
            List<Deposit> deposits = clientDB.get().getDeposits();
            List<Expense> expenses = clientDB.get().getExpenses();

            //filter deposits
            depositFilter.filterByFromDate(deposits,clientProfitRequestDto.getFromDate());
            depositFilter.filterByToDate(deposits,clientProfitRequestDto.getToDate());
            //filter expenses
            expenseFilter.filterByFromDate(expenses, clientProfitRequestDto.getFromDate());
            expenseFilter.filterByToDate(expenses, clientProfitRequestDto.getToDate());

            //sum deposits
            for(int i=0; i<deposits.size(); i++){
                totalDeposit = new BigDecimal(totalDeposit+deposits.get(i).getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
            }
            //sum expenses
            for(int i=0; i<expenses.size(); i++){
                totalExpense = new BigDecimal(totalExpense+expenses.get(i).getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
            }

            profit = new BigDecimal(totalDeposit-totalExpense).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
        }
        return new ClientProfitResponseDto(clientExist,profit,totalDeposit,totalExpense);
    }
    @Transactional
    public ClientInfo getClientInfo(String email) {
        Optional<Client> clientDB = clientRepo.findByEmail(email);
        if(clientDB.isPresent()){
            return clientMapper.map(clientDB.get());
        }
        return null;
    }
    @Transactional
    public Boolean deleteClient(String email) {
        Optional<Client> clientDB = clientRepo.findByEmail(email);
        Boolean success = false;
        if(clientDB.isPresent()){
            clientRepo.delete(clientDB.get());
            success = true;
        }
        return success;
    }
}
