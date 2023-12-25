package com.example.home_budget.mapper;

import com.example.home_budget.dto.request.RegisterClientRequestDto;
import com.example.home_budget.dto.response.ClientInfo;
import com.example.home_budget.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client map(RegisterClientRequestDto registerClientRequestDto);
    ClientInfo map(Client client);
}
