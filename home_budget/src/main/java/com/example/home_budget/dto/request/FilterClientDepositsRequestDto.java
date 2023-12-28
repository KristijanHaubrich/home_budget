package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class FilterClientDepositsRequestDto {
    private String clientEmail;
    private String fromDate;
    private String toDate;
    private Double minAmount;
    private Double maxAmount;
}
