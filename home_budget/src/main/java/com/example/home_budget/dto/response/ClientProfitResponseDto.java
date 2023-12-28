package com.example.home_budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientProfitResponseDto {
    boolean clientExist;
    Double profit;
    Double totalDeposit;
    Double totalExpense;
}
