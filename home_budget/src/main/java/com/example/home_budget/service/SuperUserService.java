package com.example.home_budget.service;

import com.example.home_budget.auth.JwtService;
import com.example.home_budget.dto.request.ChangePassSuperUserRequestDto;
import com.example.home_budget.dto.request.RegisterSuperUserRequestDto;
import com.example.home_budget.dto.response.GetAccessTokenResponseDto;
import com.example.home_budget.dto.response.SuccessResponseDto;
import com.example.home_budget.dto.response.ClientChangePassResponseDto;
import com.example.home_budget.dto.response.SuperUserInfo;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.jpa_repository.RoleRepo;
import com.example.home_budget.jpa_repository.SuperUserRepo;
import com.example.home_budget.mapper.SuperUserMapper;
import com.example.home_budget.model.Client;
import com.example.home_budget.model.Role;
import com.example.home_budget.model.SuperUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SuperUserService {
    private final SuperUserRepo userRepo;
    private final RoleRepo roleRepo;
    private final SuperUserMapper superUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepo clientRepo;
    private static final int accessTokenDuration = 1000*60*60*3; //3 sata
    private final JwtService jwtService;
    @Transactional
    public SuccessResponseDto registerSuperUser(RegisterSuperUserRequestDto registerSuperUserRequestDto) {
        Optional<SuperUser> userdb = userRepo.findByEmail(registerSuperUserRequestDto.getEmail());

        if(userdb.isEmpty()){
            Optional<Role> role = roleRepo.findByName("ROLE_SUPERUSER");
            SuperUser newSuperUser = superUserMapper.map(registerSuperUserRequestDto);
            newSuperUser.setRole(role.get());
            newSuperUser.setPassword(passwordEncoder.encode(registerSuperUserRequestDto.getPassword()));
            newSuperUser.setSuperPin(registerSuperUserRequestDto.getSuperPin());
            userRepo.save(newSuperUser);
        }
        Optional<SuperUser> newUserdb = userRepo.findByEmail(registerSuperUserRequestDto.getEmail());

        return new SuccessResponseDto(userdb.isEmpty() && newUserdb.isPresent());
    }
    @Transactional
    public ClientChangePassResponseDto changePass(ChangePassSuperUserRequestDto changePassSuperUserRequestDto) {
        Optional<SuperUser> superUserDB = userRepo.findByEmail(changePassSuperUserRequestDto.getEmail());
        boolean isSuccessfull = false;
        boolean userExist = superUserDB.isPresent();
        boolean correctOldPass = false;
        if(userExist){
            correctOldPass = passwordEncoder.matches(changePassSuperUserRequestDto.getOldPassword(),superUserDB.get().getPassword());
            if(correctOldPass) {
                String newPass = passwordEncoder.encode(changePassSuperUserRequestDto.getNewPassword());
                superUserDB.get().setPassword(newPass);
                isSuccessfull = true;
            }
        }

        return new ClientChangePassResponseDto(isSuccessfull,userExist,correctOldPass);
    }
    @Transactional
    public SuccessResponseDto changeClientPass(String clientEmail, String newPass) {
        Optional<Client> clientDB = clientRepo.findByEmail(clientEmail);
        boolean isSuccessfull = false;
        if(clientDB.isPresent()){
            String newPassword = passwordEncoder.encode(newPass);
            clientDB.get().setPassword(newPassword);
            isSuccessfull = true;
        }
        return new SuccessResponseDto(isSuccessfull);
    }
    @Transactional
    public GetAccessTokenResponseDto getAccessToken(String email) {
        Optional<SuperUser> superUserDB = userRepo.findByEmail(email);
        String accessToken = "null";

        if(superUserDB.isPresent()){
            Map<String, Object> authorities = new HashMap<>();
            List<String> permissions = new ArrayList<>();

            superUserDB.get().getAuthorities().forEach(
                    grantedAuthority -> permissions.add(grantedAuthority.getAuthority())
            );

            authorities.put("authorities", permissions);

            accessToken = jwtService.generateToken(authorities, superUserDB.get(), accessTokenDuration);
        }

        return new GetAccessTokenResponseDto(accessToken,superUserDB.isPresent());
    }
    @Transactional
    public boolean deleteSuperUser(String email) {
        Optional<SuperUser> superUser = userRepo.findByEmail(email);
        boolean success = false;
        if(superUser.isPresent()){
            userRepo.delete(superUser.get());
            success = true;
        }
        return success;
    }
    @Transactional
    public SuperUserInfo getUserInfo(String email) {
        Optional<SuperUser> superUserDB = userRepo.findByEmail(email);
        if(superUserDB.isPresent()){
            return superUserMapper.map(superUserDB.get());
        }
        return null;
    }
}
