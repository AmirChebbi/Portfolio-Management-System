package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTOMapper;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import com.ThePrince.PortfolioManagementSystem.Exceptions.DirectoryExistsException;
import com.ThePrince.PortfolioManagementSystem.Repositories.Directory.DirectoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DirectoryServiceImpl implements DirectoryService{

    private final DirectoryRepository directoryRepository;

    private final DirectoryDTOMapper directoryDTOMapper;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository, DirectoryDTOMapper directoryDTOMapper) {
        this.directoryRepository = directoryRepository;
        this.directoryDTOMapper = directoryDTOMapper;
    }

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
    public DirectoryDTO createNewDirectory(Owner owner, DirectoryPathDTO parentDirectory,DirectoryDTO directoryDTO) {
        if (!directoryRepository.existsByNameAndParentDirectory(directoryDTO.name(), parentDirectory.id())) {
            Directory newDirectory = new Directory(
                    directoryDTO.name(),
                    directoryDTO.description(),
                    owner,
                    directoryRepository.getById(parentDirectory.id()),
                    new ArrayList<Directory>()
            );
            directoryRepository.save(newDirectory);
            return directoryDTOMapper.apply(newDirectory);
        } else {
            throw new DirectoryExistsException("There is already a directory here named " + directoryDTO.name());
        }
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

    public boolean existsByNameAndParentDirectory(String name, DirectoryPathDTO parentDirectory){
        return directoryRepository.existsByNameAndParentDirectory(name,parentDirectory.id());
    }

}
