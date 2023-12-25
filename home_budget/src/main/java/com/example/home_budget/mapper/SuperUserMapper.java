package com.example.home_budget.mapper;

import com.example.home_budget.dto.request.RegisterSuperUserRequestDto;
import com.example.home_budget.dto.response.SuperUserInfo;
import com.example.home_budget.model.SuperUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperUserMapper {
    SuperUser map(RegisterSuperUserRequestDto registerSuperUserRequestDto);

    SuperUserInfo map(SuperUser superUser);
}
