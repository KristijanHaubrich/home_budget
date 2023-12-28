package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class CreateDepositRequestDto {
    private String clientEmail;
    private String description;
    private String date;
    private Double amount;

}
