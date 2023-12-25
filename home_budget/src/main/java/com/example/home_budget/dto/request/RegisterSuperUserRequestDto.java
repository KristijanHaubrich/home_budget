package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class RegisterSuperUserRequestDto {
    private String name;
    private String password;
    private String email;
    private String superPin;
}
