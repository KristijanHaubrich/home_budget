package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class ChangeClientPassRequestDto {
    private String email;
    private String oldPass;
    private String newPass;
}
