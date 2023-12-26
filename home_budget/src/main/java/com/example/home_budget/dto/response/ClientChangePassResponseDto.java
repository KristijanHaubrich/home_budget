package com.example.home_budget.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientChangePassResponseDto {
    private boolean isSuccessfull;
    private boolean userExist;
    private boolean correctOldPass;
}
