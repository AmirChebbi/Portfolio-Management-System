package com.ThePrince.PortfolioManagementSystem.Services.Token;

import com.ThePrince.PortfolioManagementSystem.DAOs.Token.Token;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface TokenService {
    public Token getTokenByToken(final String token);
    public List<Token> fetchAllValidTokenByUserId(final UUID userId);
    public Token save(@NotNull final Token token);
    public List<Token> saveAll(List<Token> tokens);
    public Token generateToken(UserEntity userEntity);
    public Token generateRefreshToken(UserEntity userEntity, Token token);
}