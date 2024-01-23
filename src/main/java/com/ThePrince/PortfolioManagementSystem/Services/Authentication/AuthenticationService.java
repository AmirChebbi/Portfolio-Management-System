package com.ThePrince.PortfolioManagementSystem.Services.Authentication;

import com.ThePrince.PortfolioManagementSystem.DTOs.Authentication.LoginDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Authentication.RegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    public ResponseEntity<Object> register(RegistrationDTO registrationDTO);
    public ResponseEntity<Object> login(LoginDTO loginDTO);
    public ResponseEntity<Object> confirmation(String ConfirmationToken);
    public ResponseEntity<Object> disableAccount(String token);
    public void refresh(String token);
    public ResponseEntity<Object> logout(String token);
}
