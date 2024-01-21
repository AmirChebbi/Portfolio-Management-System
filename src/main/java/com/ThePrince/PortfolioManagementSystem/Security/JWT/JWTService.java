package com.ThePrince.PortfolioManagementSystem.Security.JWT;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import com.ThePrince.PortfolioManagementSystem.Security.Utility.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Service
@Component
public class JWTService {
    public String generateToken(UserEntity userEntity){
        Date iat = new Date((System.currentTimeMillis()));
        Date eat = new Date(System.currentTimeMillis() + SecurityConstants.JWT_ACCESS_TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setIssuedAt(iat)
                .setExpiration(eat)
                .signWith(SignatureAlgorithm.HS256,getSignatureKey())
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Claims claims = extractAllClaims(token);
        } catch (ExpiredJwtException exception){
            throw new ExpiredJwtException(null,null,"Token has expire, please login again !",exception);
        }
        return true;
    }

    public <T> T extractClaims(String token, @NotNull Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractAllClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException){
            String failureMessage = String.format("Token has expire, please login again !");
            throw new ExpiredJwtException(null,null,failureMessage,expiredJwtException);
        }
    }

    public String extractEmailFromToken(String token ){
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpirationFromToken(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public Key getSignatureKey(){
        byte [] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_ACCESS_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
