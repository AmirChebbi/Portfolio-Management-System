package com.ThePrince.PortfolioManagementSystem.Services.Authentication;

import com.ThePrince.PortfolioManagementSystem.DTOs.Authentication.LoginDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Authentication.RegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Override
    public ResponseEntity<Object> register(RegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Object> login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Object> confirmation(String ConfirmationToken) {
        return null;
    }

    @Override
    public ResponseEntity<Object> disableAccount(String token) {
        return null;
    }

    @Override
    public void refresh(String token) {

    }

    @Override
    public ResponseEntity<Object> logout(String token) {
        return null;
    }
}
