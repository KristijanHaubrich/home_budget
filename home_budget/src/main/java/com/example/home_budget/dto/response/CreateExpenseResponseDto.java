package com.example.home_budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateExpenseResponseDto {
    private boolean clientExist;
    private boolean categoryExist;
    private boolean isDateValid;
    private boolean isSuccess;

}
