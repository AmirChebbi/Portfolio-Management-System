package com.ThePrince.PortfolioManagementSystem.Services.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserService {
    public ResponseEntity<Object> getUserById(UUID id);
    public ResponseEntity<Object> getAllByRole(long pageNumber, String role);
    public ResponseEntity<Object> getAll(long pageNumber);
    public ResponseEntity<Object> viewProfile(UserDetails userDetails);
    public ResponseEntity<Object> enableOrDisableUser(UUID id, boolean enable);
    public ResponseEntity<Object> enableById(UUID id);
    public UserEntity getByEmail(String email);
    public boolean isEmailRegistered(String email);


}
