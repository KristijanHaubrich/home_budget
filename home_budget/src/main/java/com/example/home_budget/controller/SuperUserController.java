package com.example.home_budget.controller;

import com.example.home_budget.dto.request.ChangePassSuperUserRequestDto;
import com.example.home_budget.dto.request.RegisterSuperUserRequestDto;
import com.example.home_budget.dto.response.GetAccessTokenResponseDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.dto.response.ClientChangePassResponseDto;
import com.example.home_budget.service.SuperUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superusers/")
public class SuperUserController {
    private final SuperUserService superUserService;
    @GetMapping("getAccessToken/{email}")
    @PreAuthorize("hasAuthority('GET_SUPER_USER_ACCESS_TOKEN')")
    public GetAccessTokenResponseDto getAccessToken(@PathVariable String email){
        return superUserService.getAccessToken(email);
    }
    @PostMapping("registerSuperUser")
    @PreAuthorize("hasAuthority('REGISTER_SUPER_USER')")
    public SuccessResponseDto registerSuperUser(@RequestBody RegisterSuperUserRequestDto registerSuperUserRequestDto){
        return superUserService.registerSuperUser(registerSuperUserRequestDto);
    }
    @PatchMapping("changePass")
    @PreAuthorize("hasAuthority('CHANGE_SUPER_USER_PASSWORD')")
    public ClientChangePassResponseDto changePass(@RequestBody ChangePassSuperUserRequestDto changePassSuperUserRequestDto){
        return  superUserService.changePass(changePassSuperUserRequestDto);
    }
    @PatchMapping("changeClientPass/{clientEmail}/{newPass}")
    @PreAuthorize("hasAuthority('CHANGE_CLIENT_PASSWORD_DIRECTLY')")
    public SuccessResponseDto changeClientPass(@PathVariable String clientEmail,@PathVariable String newPass){
        return  superUserService.changeClientPass(clientEmail,newPass);
    }
    @DeleteMapping("{email}")
    @PreAuthorize("hasAuthority('DELETE_SUPER_USER')")
    public boolean deleteSuperUser(@PathVariable String email){
        return superUserService.deleteSuperUser(email);
    }
}
