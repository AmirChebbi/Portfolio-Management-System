package com.ThePrince.PortfolioManagementSystem.DTOs.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.Role.Role;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String fistName,
        String lastName,
        String email,
        Role role,
        String phoneNumber
) {
}
