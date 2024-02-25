package com.ThePrince.PortfolioManagementSystem.Security.JWT;

import com.ThePrince.PortfolioManagementSystem.DAOs.Token.Token;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import com.ThePrince.PortfolioManagementSystem.Exceptions.ExpiredTokenException;
import com.ThePrince.PortfolioManagementSystem.Exceptions.InvalidTokenException;
import com.ThePrince.PortfolioManagementSystem.Exceptions.ResourceNotFoundException;
import com.ThePrince.PortfolioManagementSystem.Exceptions.RevokedTokenException;
import com.ThePrince.PortfolioManagementSystem.Handler.ResponseHandler;
import com.ThePrince.PortfolioManagementSystem.Repositories.Token.TokenRepository;
import com.ThePrince.PortfolioManagementSystem.Services.UserDetails.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
            if (Objects.nonNull(email) || Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())){
                filterChain.doFilter(request, response);
                return;
            }
            Token token = tokenRepository.getTokenByToken(tokenValue).orElseThrow(() -> new ResourceNotFoundException("Token doesn't exist!!"));

            if (token.isExpired()){
                throw new ExpiredTokenException("Token is expired, please login again");
            }
            if (token.isRevoked()){
                throw new RevokedTokenException("Token is revoked, please login again");
            }
            if (jwtService.userTokenValid(tokenValue,user)){
                throw new InvalidTokenException("Token is not valid");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception e){
            log.error("Error logging in :" + e.getMessage());
            response.setHeader("error!!" , e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            new ObjectMapper().writeValue(response.getOutputStream(),ResponseHandler.generateErrorResponse(e.getMessage(),HttpStatus.FORBIDDEN));
        }
    }
}
