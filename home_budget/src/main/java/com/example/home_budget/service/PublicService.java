package com.example.home_budget.service;

import com.example.home_budget.auth.JwtService;
import com.example.home_budget.dto.request.RegisterClientRequestDto;
import com.example.home_budget.dto.response.*;
import com.example.home_budget.jpa_repository.ClientRepo;
import com.example.home_budget.jpa_repository.RoleRepo;
import com.example.home_budget.jpa_repository.SuperUserRepo;
import com.example.home_budget.jpa_repository.UserRepo;
import com.example.home_budget.mapper.ClientMapper;
import com.example.home_budget.mapper.SuperUserMapper;
import com.example.home_budget.model.Client;
import com.example.home_budget.model.Role;
import com.example.home_budget.model.SuperUser;
import com.example.home_budget.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PublicService {
    private static final double predefinedAmount = 1000;
    private static final int accessTokenDuration = 1000*60*60*3; //3 sata
    private static final int refreshTokenDuration = 1000*60*60*24*20; //20 dana
    private final SuperUserRepo superUserRepo;
    private final SuperUserMapper superUserMapper;
    private final JwtService jwtService;
    private final ClientRepo clientRepo;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;
    @Transactional
    public SuccessResponseDto registerClient(RegisterClientRequestDto registerClientDto) {
        Optional<Users> userdb = userRepo.findByEmail(registerClientDto.getEmail());

        if(userdb.isEmpty()){
            Optional<Role> role = roleRepo.findByName("ROLE_CLIENT");
            Client newClient = clientMapper.map(registerClientDto);
            newClient.setRole(role.get());
            newClient.setPassword(passwordEncoder.encode(registerClientDto.getPassword()));
            newClient.setBalance(predefinedAmount);
            userRepo.save(newClient);
        }
        Optional<Users> newUserdb = userRepo.findByEmail(registerClientDto.getEmail());

        return new SuccessResponseDto(userdb.isEmpty() && newUserdb.isPresent());
    }
    
    @Transactional
    public AuthClientResponseDto authClient(String email, String password) {
        Optional<Client> clientdb = clientRepo.findByEmail(email);
        boolean isPassWrong = true;
        boolean isSuccessfull = false;
        String accessToken = "null";
        String refreshToken = "null";
        ClientInfo client = null;

        if(clientdb.isPresent()) {
            isPassWrong = !passwordEncoder.matches(password, clientdb.get().getPassword());
            isSuccessfull = !isPassWrong && clientdb.isPresent();

            if (!isPassWrong) {

                Map<String, Object> authorities = new HashMap<>();
                List<String> permissions = new ArrayList<>();

                clientdb.get().getAuthorities().forEach(
                        grantedAuthority -> permissions.add(grantedAuthority.getAuthority())
                );

                authorities.put("authorities", permissions);

                accessToken = jwtService.generateToken(authorities, clientdb.get(), accessTokenDuration);
                refreshToken = jwtService.generateToken(authorities, clientdb.get(), refreshTokenDuration);

                client = clientMapper.map(clientdb.get());
            }
        }
        return new AuthClientResponseDto(isSuccessfull,isPassWrong,clientdb.isEmpty(),client,accessToken,refreshToken);
    }

    @Transactional
    public AuthSuperUserResponseDto authSuperUser(String email, String password, String superPin) {
        boolean isSuccessfull = false;
        boolean wrongPass = true;
        boolean wrongSuperPin = true;
        boolean userNotExisting;
        String refreshToken = "null";
        String accessToken = "null";
        SuperUserInfo superUserInfo = null;

        Optional<SuperUser> superUserDB =  superUserRepo.findByEmail(email);
        userNotExisting = superUserDB.isEmpty();

        if(!userNotExisting){
            SuperUser superUser = superUserDB.get();
            superUserInfo = superUserMapper.map(superUser);

            wrongPass = !passwordEncoder.matches(password,superUser.getPassword());
            wrongSuperPin = !superPin.equals(superUser.getSuperPin());

            if(!wrongPass && !wrongSuperPin){
                Map<String, Object> authorities = new HashMap<>();
                List<String> permissions = new ArrayList<>();

                superUser.getAuthorities().forEach(
                        grantedAuthority -> permissions.add(grantedAuthority.getAuthority())
                );

                authorities.put("authorities", permissions);

                accessToken = jwtService.generateToken(authorities, superUser, accessTokenDuration);
                refreshToken = jwtService.generateToken(authorities, superUser, refreshTokenDuration);
                isSuccessfull = true;
            }
        }

        return new AuthSuperUserResponseDto(isSuccessfull,wrongPass,userNotExisting,wrongSuperPin,superUserInfo,refreshToken,accessToken);
    }
}
