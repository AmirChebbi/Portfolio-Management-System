package com.ThePrince.PortfolioManagementSystem.Services.Token;

import com.ThePrince.PortfolioManagementSystem.DAOs.Token.Token;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;

public interface TokenService {
    public Token getTokenByToken(String token);
    public Token generateToken(UserEntity userEntity);
    public Token generateRefreshToken(UserEntity userEntity, Token token);

}
