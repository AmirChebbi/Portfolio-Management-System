package com.ThePrince.PortfolioManagementSystem.DTOs.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.Repositories.Directory.DirectoryRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class DirectoryDTOMapper implements Function<Directory, DirectoryDTO> {

    private final DirectoryPathMapper directoryPathMapper;

//    private final DirectoryRepository directoryRepository;
    public DirectoryDTOMapper(DirectoryPathMapper directoryPathMapper) {
        this.directoryPathMapper = directoryPathMapper;
    }

    @Override
    public DirectoryDTO apply(Directory directory) {
        return new DirectoryDTO(
                directory.getId(),
                directory.getName(),
                directory.getDescription(),
                directory.getOwner().getFistName() +" "+ directory.getOwner().getLastName(),
                directory.getOwner().getEmail(),
                directory.getParentDirectory().getId(),
                directory.getParentDirectory().getName(),
                directory.getChildren().stream().map(directoryPathMapper).toList()
        );
    }


}
