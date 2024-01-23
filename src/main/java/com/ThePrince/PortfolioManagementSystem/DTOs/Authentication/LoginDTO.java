package com.ThePrince.PortfolioManagementSystem.DTOs.Authentication;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDTO {
    @Email(message = "Invalid email format")
    private String email;

    private String password;

}
