package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class FilterClientExpensesRequestDto {
    private String clientEmail;
    private String category;
    private String fromDate;
    private String toDate;
    private Double minAmount;
    private Double maxAmount;
}
