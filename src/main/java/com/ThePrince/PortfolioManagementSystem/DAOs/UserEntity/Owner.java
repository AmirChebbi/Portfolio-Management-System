package com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import jakarta.persistence.*;


@Entity
public class Owner extends UserEntity{

    @OneToOne
    @JoinColumn()
    private Directory genesisDirectory;

}
