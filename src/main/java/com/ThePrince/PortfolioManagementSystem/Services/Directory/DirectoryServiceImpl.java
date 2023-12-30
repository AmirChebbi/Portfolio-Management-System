package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DirectoryServiceImpl implements DirectoryService{
    @Override
    public Directory intializeGenesisDirectory(Owner owner) {
        return new Directory(
                owner.getFistName()+" "+owner.getLastName(),
                null,
                owner,
                null,
                new ArrayList<Directory>()
        );
    }

    @Override
    public DirectoryDTO createNewDirectory(Owner owner, DirectoryPathDTO parentDirectory) {
        return null;
    }

    @Override
    public DirectoryDTO getDirectoryById(long id) {
        return null;
    }

    @Override
    public List<DirectoryPathDTO> findDirectoryPath(long id) {
        return null;
    }

    @Override
    public DirectoryDTO moveDirectory(long id, long newParentDirectoryId) {
        return null;
    }

    @Override
    public List<DirectoryPathDTO> deleteDirectoryById(long id) {
        return null;
    }

    @Override
    public DirectoryDTO copyDirectory(long id, long parentId) {
        return null;
    }

    @Override
    public void updateDirectory(long id, DirectoryDTO directoryDTO) {

    }
}
