package com.example.home_budget.service;

import com.example.home_budget.dto.request.RegisterSuperUserRequestDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.jpa_repository.RoleRepo;
import com.example.home_budget.jpa_repository.UserRepo;
import com.example.home_budget.mapper.SuperUserMapper;
import com.example.home_budget.model.Role;
import com.example.home_budget.model.SuperUser;
import com.example.home_budget.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuperUserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final SuperUserMapper superUserMapper;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public SuccessResponseDto registerSuperUser(RegisterSuperUserRequestDto registerSuperUserRequestDto) {
        Optional<Users> userdb = userRepo.findByEmail(registerSuperUserRequestDto.getEmail());

        if(userdb.isEmpty()){
            Optional<Role> role = roleRepo.findByName("ROLE_SUPERUSER");
            SuperUser newSuperUser = superUserMapper.map(registerSuperUserRequestDto);
            newSuperUser.setRole(role.get());
            newSuperUser.setPassword(passwordEncoder.encode(registerSuperUserRequestDto.getPassword()));
            newSuperUser.setSuperPin(registerSuperUserRequestDto.getSuperPin());
            userRepo.save(newSuperUser);
        }
        Optional<Users> newUserdb = userRepo.findByEmail(registerSuperUserRequestDto.getEmail());

        return new SuccessResponseDto(userdb.isEmpty() && newUserdb.isPresent());
    }
}
