package com.example.home_budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCategoryResponseDto {
    private boolean categoryAlreadyExist;
    private boolean validName;
    private boolean success;
}
