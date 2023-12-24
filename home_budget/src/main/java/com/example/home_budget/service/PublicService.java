package com.example.home_budget.service;

import com.example.home_budget.dto.request.RegisterClientRequestDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.jpa_repository.RoleRepo;
import com.example.home_budget.jpa_repository.UserRepo;
import com.example.home_budget.mapper.ClientMapper;
import com.example.home_budget.model.Client;
import com.example.home_budget.model.Role;
import com.example.home_budget.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicService {
    private static final double predefinedAmount = 1000;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;
    @Transactional
    public SuccessResponseDto registerClient(RegisterClientRequestDto registerClientDto) {
        Optional<Users> userdb = userRepo.findByEmail(registerClientDto.getEmail());

        if(!userdb.isPresent()){
            Optional<Role> role = roleRepo.findByName("ROLE_CLIENT");
            Client newClient = clientMapper.map(registerClientDto);
            newClient.setRole(role.get());
            newClient.setPassword(passwordEncoder.encode(registerClientDto.getPassword()));
            newClient.setBalance(predefinedAmount);
            userRepo.save(newClient);
        }
        Optional<Users> newUserdb = userRepo.findByEmail(registerClientDto.getEmail());

        return new SuccessResponseDto(!userdb.isPresent() && newUserdb.isPresent());
    }




}
