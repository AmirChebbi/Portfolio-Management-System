package com.ThePrince.PortfolioManagementSystem.Repositories.Token;

import com.ThePrince.PortfolioManagementSystem.DAOs.Token.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t from Token t where t.token=:token")
    Optional<Token> getTokenByToken(@Param("token") String token);
}
