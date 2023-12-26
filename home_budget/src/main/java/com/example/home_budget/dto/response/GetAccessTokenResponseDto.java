package com.example.home_budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAccessTokenResponseDto {
    private String accessToken;
    private boolean clientExist;
}
