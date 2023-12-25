package com.example.home_budget.controller;

import com.example.home_budget.dto.request.RegisterClientRequestDto;
import com.example.home_budget.dto.response.AuthClientResponseDto;
import com.example.home_budget.dto.response.AuthSuperUserResponseDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.service.PublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/")
public class PublicController {
    private final PublicService publicService;
    @GetMapping("authClient/{email}/{password}")
    public AuthClientResponseDto authClient(@PathVariable String email,@PathVariable String password){
        return publicService.authClient(email, password);
    }
    @PostMapping("registerClient")
    public SuccessResponseDto registerClient(@RequestBody RegisterClientRequestDto registerClientDto){
        return publicService.registerClient(registerClientDto);
    }

    @GetMapping("authSuperUser/{email}/{password}/{superPin}")
    public AuthSuperUserResponseDto authSuperUser(@PathVariable String email, @PathVariable String password, @PathVariable String superPin){
        return publicService.authSuperUser(email,password,superPin);
    }
}
