package com.ThePrince.PortfolioManagementSystem.Security.JWT;

import com.ThePrince.PortfolioManagementSystem.DAOs.Token.Token;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import com.ThePrince.PortfolioManagementSystem.Repositories.Token.TokenRepository;
import com.ThePrince.PortfolioManagementSystem.Services.UserDetails.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,@NotNull HttpServletResponse response,@NotNull FilterChain filterChain) throws ServletException, IOException {

        try{
            final String authHeader = request.getHeader("Authorization");
            final String tokenValue = authHeader.substring(7);
            final String email = jwtService.extractEmailFromToken(tokenValue);
            UserEntity user = (UserEntity) customUserDetailsService.loadUserByUsername(email);

            if (authHeader == null || authHeader.startsWith("Bearer")){
                filterChain.doFilter(request,response);
                return;
            }
            if (jwtService.validateToken(tokenValue)){
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.userTokenValid(tokenValue,user)){
                filterChain.doFilter(request,response);
                return;
            }

            if (jwtService.tokenExpired(tokenValue)){
                filterChain.doFilter(request,response);
                return;
            }
            if (Objects.isNull(email) || Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())){
                filterChain.doFilter(request, response);
                return;
            }
            Token token = tokenRepository.getTokenByToken(tokenValue).orElse(null);
            if (token.isExpired() || token.isRevoked()){

            }
        } catch (Exception e){

        }
    }
}
