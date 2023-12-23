package com.example.home_budget.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper mapper;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        if(authHeader==null  || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
        }else{
            try{
                jwt = authHeader.substring(7);
                email = jwtService.extractEmail(jwt);
                Claims claims = jwtService.extractAllClaims(jwt);
                List<String> authorities = claims.get("authorities", List.class);

                authorities.forEach(
                        authority -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority))
                );
                if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                    if(jwtService.isTokenValid(jwt,userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                simpleGrantedAuthorities
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
                filterChain.doFilter(request,response);
            }catch(ExpiredJwtException e){
                Map<String, Object> errorDetails = new HashMap<>();
                errorDetails.put("expiredToken", "Token is expired");

                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                mapper.writeValue(response.getWriter(), errorDetails);
            }

        }

    }
}

