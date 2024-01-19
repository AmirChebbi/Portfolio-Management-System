package com.ThePrince.PortfolioManagementSystem.DTOs.Directory;

public record DirectoryUpdater(
        long id,
        String name,
        String description,
        boolean isVisible
) {
}
