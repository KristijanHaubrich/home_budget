package com.example.home_budget.controller;

import com.example.home_budget.dto.request.ChangePassSuperUserRequestDto;
import com.example.home_budget.dto.request.RegisterSuperUserRequestDto;
import com.example.home_budget.dto.response.GetAccessTokenResponseDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.dto.response.ClientChangePassResponseDto;
import com.example.home_budget.service.SuperUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superusers/")
public class SuperUserController {
    private final SuperUserService superUserService;
    @GetMapping("getAccessToken/{email}")
    public GetAccessTokenResponseDto getAccessToken(@PathVariable String email){
        return superUserService.getAccessToken(email);
    }
    @PostMapping("registerSuperUser")
    public SuccessResponseDto registerSuperUser(@RequestBody RegisterSuperUserRequestDto registerSuperUserRequestDto){
        return superUserService.registerSuperUser(registerSuperUserRequestDto);
    }
    @PatchMapping("changePass")
    public ClientChangePassResponseDto changePass(@RequestBody ChangePassSuperUserRequestDto changePassSuperUserRequestDto){
        return  superUserService.changePass(changePassSuperUserRequestDto);
    }
    @PatchMapping("changeClientPass/{clientEmail}/{newPass}")
    public SuccessResponseDto changeClientPass(@PathVariable String clientEmail,@PathVariable String newPass){
        return  superUserService.changeClientPass(clientEmail,newPass);
    }
}
