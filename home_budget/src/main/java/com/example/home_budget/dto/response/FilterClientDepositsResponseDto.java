package com.example.home_budget.dto.response;

import com.example.home_budget.model.Deposit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilterClientDepositsResponseDto {
    private boolean clientExist;
    boolean areDatesValid;
    List<Deposit> deposits;
}
