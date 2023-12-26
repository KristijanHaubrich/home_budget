package com.example.home_budget.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientChangePassResponseDto {
    private boolean clientExist;
    private boolean wrongOldPass;
    private boolean isSuccessfull;
}
