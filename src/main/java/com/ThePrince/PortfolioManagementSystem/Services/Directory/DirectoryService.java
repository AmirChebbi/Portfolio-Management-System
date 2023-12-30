package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import org.springframework.stereotype.Service;

@Service
public interface DirectoryService {

    public Directory intializeGenesisDirectory(Owner owner);

    public DirectoryDTO createNewDirectory(Owner owner, DirectoryPathDTO parentDirectory);

    public DirectoryDTO getDirectoryById(long id);




}
