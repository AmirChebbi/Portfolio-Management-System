package com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.Role.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Visitor extends UserEntity{

    @Id
    private UUID id;

}
