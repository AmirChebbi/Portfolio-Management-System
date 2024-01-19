package com.ThePrince.PortfolioManagementSystem.DTOs.Directory;

import java.util.List;

public record DirectoryDTO(
        long id,
        String name,
        String description,
        String ownerName,
        String ownerEmail,
        long parentDirectoryId,
        String parentDirectoryName,
        List<DirectoryPathDTO> children,
        boolean isVisible
) {}