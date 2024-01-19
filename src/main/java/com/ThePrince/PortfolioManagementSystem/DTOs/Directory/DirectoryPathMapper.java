package com.ThePrince.PortfolioManagementSystem.DTOs.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DirectoryPathMapper implements Function<Directory,DirectoryPathDTO> {

    @Override
    public DirectoryPathDTO apply(Directory directory) {
        return new DirectoryPathDTO(
                directory.getId(),
                directory.getName(),
                directory.isVisible()
        );
    }
}
