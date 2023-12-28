package com.example.home_budget.service;

import com.example.home_budget.dto.request.CreateDepositRequestDto;
import com.example.home_budget.dto.request.DepositsFilterRequestDto;
import com.example.home_budget.dto.response.CreateDepositResponseDto;
import com.example.home_budget.dto.response.DepositsFilterResponseDto;
import com.example.home_budget.filters.DepositFilter;
import com.example.home_budget.jpa_repository.CategoryRepo;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.jpa_repository.DepositRepo;
import com.example.home_budget.mapper.DepositMapper;
import com.example.home_budget.model.Category;
import com.example.home_budget.model.Client;
import com.example.home_budget.model.Deposit;
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
public class DepositService {
    private final DepositRepo depositRepo;
    private final DepositFilter depositFilter;
    private final ClientRepo clientRepo;
    private final DepositMapper depositMapper;
    private final CategoryRepo categoryRepo;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    @Transactional
    public List<Deposit> getAllDeposits() {
        return depositRepo.findAll();
    }
    @Transactional
    public Deposit getDepositById(Long id) {
        return depositRepo.getReferenceById(id);
    }
    @Transactional
    public DepositsFilterResponseDto filterDeposits(DepositsFilterRequestDto depositsFilterRequestDto) {
        try{
            List<Deposit> deposits = depositRepo.findAll();

            depositFilter.filterByFromDate(deposits,depositsFilterRequestDto.getFromDate());
            depositFilter.filterByToDate(deposits,depositsFilterRequestDto.getToDate());
            depositFilter.filterByMinAmount(deposits,depositsFilterRequestDto.getMinAmount());
            depositFilter.filterByMaxAmount(deposits,depositsFilterRequestDto.getMaxAmount());

            return new DepositsFilterResponseDto(true,deposits);

        }catch (DateTimeParseException e){
            return new DepositsFilterResponseDto(false,new ArrayList<>());
        }
    }
    @Transactional
    public CreateDepositResponseDto createDeposit(CreateDepositRequestDto createDepositRequestDto) {
        try{
            Optional<Client> clientDB = clientRepo.findByEmail(createDepositRequestDto.getClientEmail());
            boolean clientExist = clientDB.isPresent();
            boolean isSuccess = false;
            if(clientExist && createDepositRequestDto.getAmount() > 0){
                LocalDateTime.parse(createDepositRequestDto.getDate(),dtf);
                Deposit deposit = depositMapper.map(createDepositRequestDto);
                deposit.setClient(clientDB.get());
                Double realAmount = new BigDecimal(deposit.getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
                deposit.setAmount(realAmount);
                Double newBalance = new BigDecimal(clientDB.get().getBalance()+deposit.getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
                clientDB.get().setBalance(newBalance);
                depositRepo.save(deposit);
                isSuccess = true;
            }
            return new CreateDepositResponseDto(clientExist,true,isSuccess);
        }catch(DateTimeParseException e){
            return new CreateDepositResponseDto(true,false,false);
        }
    }
    @Transactional
    public boolean deleteDeposit(Long id) {
        Optional<Deposit> depositDB = depositRepo.findById(id);
        boolean success = false;
        if(depositDB.isPresent()){
            depositRepo.delete(depositDB.get());
            success = true;
        }
        return success;
    }
}
