package com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
@DiscriminatorValue(value = "VISITOR")
public class Visitor extends UserEntity{

    @Id
    private UUID id;


}
