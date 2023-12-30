package com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@DiscriminatorValue("OWNER")
public class Owner extends UserEntity{
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn()
    private Directory genesisDirectory;
}
