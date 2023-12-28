package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class CreateExpenseRequestDto {
    private String clientEmail;
    private String categoryName;
    private String description;
    private String date;
    private Double amount;
}
