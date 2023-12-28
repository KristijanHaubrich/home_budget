package com.example.home_budget.dto.request;

import lombok.Data;
//fields that user want to exclude (from filtering) must have value of -1 ("-1" for String and -1 for double)
@Data
public class DepositsFilterRequestDto {
    private String fromDate;
    private String toDate;
    private Double minAmount;
    private Double maxAmount;
}
