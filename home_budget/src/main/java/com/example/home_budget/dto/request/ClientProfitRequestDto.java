package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class ClientProfitRequestDto {
    private String clientEmail;
    private String fromDate;
    private String toDate;
}
