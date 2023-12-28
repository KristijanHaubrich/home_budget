package com.example.home_budget.dto.request;

import lombok.Data;
@Data
//fields that user want to exclude (from filtering) must have value of -1 ("-1" for String and -1 for double)
public class ExpensesFilterRequestDto {
    private String category;
    private String fromDate;
    private String toDate;
    private Double minAmount;
    private Double maxAmount;
}
