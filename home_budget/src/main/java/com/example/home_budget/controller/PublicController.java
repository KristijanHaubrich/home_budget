package com.example.home_budget.controller;

import com.example.home_budget.dto.request.RegisterClientRequestDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.service.PublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/")
public class PublicController {
    private final PublicService publicService;
    @PostMapping("registerClient")
    public SuccessResponseDto registerClient(@RequestBody RegisterClientRequestDto registerClientDto){
        return publicService.registerClient(registerClientDto);
    }

}
