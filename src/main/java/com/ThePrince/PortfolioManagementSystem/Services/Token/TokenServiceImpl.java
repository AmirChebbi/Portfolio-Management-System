package com.ThePrince.PortfolioManagementSystem.Services.Token;

import com.ThePrince.PortfolioManagementSystem.DAOs.Token.Token;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class TokenServiceImpl implements TokenService{
    @Override
    public Token getTokenByToken(String token) {
        return null;
    }

    @Override
    public List<Token> fetchAllValidTokenByUserId(UUID userId) {
        return null;
    }

    @Override
    public Token save(@NotNull Token token) {
        return null;
    }

    @Override
    public List<Token> saveAll(List<Token> tokens) {
        return null;
    }

    @Override
    public Token generateToken(UserEntity userEntity) {
        return null;
    }

    @Override
    public Token generateRefreshToken(UserEntity userEntity, Token token) {
        return null;
    }
}
