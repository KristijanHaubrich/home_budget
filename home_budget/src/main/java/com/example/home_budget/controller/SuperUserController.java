package com.example.home_budget.controller;

import com.example.home_budget.dto.request.RegisterSuperUserRequestDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.service.SuperUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/superusers/")
public class SuperUserController {
    private final SuperUserService superUserService;
    @PostMapping("registerSuperUser")
    public SuccessResponseDto registerSuperUser(@RequestBody RegisterSuperUserRequestDto registerSuperUserRequestDto){
        return superUserService.registerSuperUser(registerSuperUserRequestDto);
    }
}
