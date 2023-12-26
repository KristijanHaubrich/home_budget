package com.example.home_budget.dto.request;

import lombok.Data;

@Data
public class ChangePassSuperUserRequestDto {
    private String oldPassword;
    private String newPassword;
    private String email;
}
