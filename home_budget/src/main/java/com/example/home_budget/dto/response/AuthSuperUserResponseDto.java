package com.example.home_budget.dto.response;

import com.example.home_budget.model.SuperUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthSuperUserResponseDto {
    private boolean isSuccessfull;
    private boolean wrongPass;
    private boolean userNotExisting;
    private boolean wrongSuperPin;
    private SuperUserInfo superUser;
    private String refreshToken;
    private String accessToken;
}
