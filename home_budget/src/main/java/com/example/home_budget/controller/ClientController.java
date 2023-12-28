package com.example.home_budget.controller;

import com.example.home_budget.dto.request.ChangeClientPassRequestDto;
import com.example.home_budget.dto.response.ClientChangePassResponseDto;
import com.example.home_budget.dto.response.GetAccessTokenResponseDto;
import com.example.home_budget.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients/")
public class ClientController {
    private final ClientService clientService;
    @GetMapping("getAccessToken/{email}")
    public GetAccessTokenResponseDto getAccessToken(@PathVariable String email){
        return clientService.getAccessToken(email);
    }
    
    @PatchMapping("changePass")
    public ClientChangePassResponseDto changePass(@RequestBody ChangeClientPassRequestDto changeClientPassRequestDto){
        return clientService.changePass(changeClientPassRequestDto);
    }
}
