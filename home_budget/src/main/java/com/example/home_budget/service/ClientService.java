package com.example.home_budget.service;

import com.example.home_budget.auth.JwtService;
import com.example.home_budget.dto.request.ChangeClientPassRequestDto;
import com.example.home_budget.dto.response.ClientChangePassResponseDto;
import com.example.home_budget.dto.response.GetAccessTokenResponseDto;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final int accessTokenDuration = 1000*60*60*3; //3 sata
    @Transactional
    public ClientChangePassResponseDto changePass(ChangeClientPassRequestDto changeClientPassRequestDto) {
        Optional<Client> clientDB = clientRepo.findByEmail(changeClientPassRequestDto.getEmail());
        boolean clientExist = clientDB.isPresent();
        boolean correctOldPass = false;
        boolean isSuccessfull = false;
        if(clientExist){
            correctOldPass = passwordEncoder.matches(changeClientPassRequestDto.getOldPass(),clientDB.get().getPassword());
            if(correctOldPass){
                String newPassword = passwordEncoder.encode(changeClientPassRequestDto.getNewPass());
                clientDB.get().setPassword(newPassword);
                isSuccessfull = true;
            }
        }

        return new ClientChangePassResponseDto(isSuccessfull,clientExist,correctOldPass);
    }
    @Transactional
    public GetAccessTokenResponseDto getAccessToken(String email) {
        Optional<Client> clientDB = clientRepo.findByEmail(email);
        String accessToken = "null";

        if(clientDB.isPresent()){
            Map<String, Object> authorities = new HashMap<>();
            List<String> permissions = new ArrayList<>();

            clientDB.get().getAuthorities().forEach(
                    grantedAuthority -> permissions.add(grantedAuthority.getAuthority())
            );

            authorities.put("authorities", permissions);

            accessToken = jwtService.generateToken(authorities, clientDB.get(), accessTokenDuration);
        }

        return new GetAccessTokenResponseDto(accessToken,clientDB.isPresent());
    }
}
