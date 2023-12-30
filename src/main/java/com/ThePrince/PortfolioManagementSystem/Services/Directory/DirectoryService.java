package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DirectoryService {

    public Directory intializeGenesisDirectory(Owner owner); //creates the first Directory given to a new Portfolio Owner

    public DirectoryDTO createNewDirectory(Owner owner, DirectoryPathDTO parentDirectory);

    public DirectoryDTO getDirectoryById(long id);

    public List<DirectoryPathDTO> findDirectoryPath(long id); //returns the directory Path

    public DirectoryDTO moveDirectory(long id, long newParentDirectoryId);   //returns the new Parent Directory

    public List<DirectoryPathDTO> deleteDirectoryById(long id); //returns all the child directories that were deleted with it

    public DirectoryDTO copyDirectory(long id, long parentId);

    public void updateDirectory(long id, DirectoryDTO directoryDTO);
}
