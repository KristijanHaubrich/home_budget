package com.example.home_budget.mapper;

import com.example.home_budget.dto.request.CreateDepositRequestDto;
import com.example.home_budget.model.Deposit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositMapper {
    Deposit map(CreateDepositRequestDto createDepositRequestDto);
}
