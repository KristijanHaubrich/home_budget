package com.example.home_budget.dto.response;

import com.example.home_budget.model.Client;
import com.example.home_budget.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthClientResponseDto {
    private boolean isSuccessfull;
    private boolean wrongPass;
    private boolean clientNotExisting;
    private ClientInfo client;
    private String accessToken;
    private String refreshToken;
}
