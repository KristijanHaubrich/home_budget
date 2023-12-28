package com.example.home_budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDepositResponseDto {
    private boolean clientExist;
    private boolean isDateValid;
    private boolean isSuccess;
}
