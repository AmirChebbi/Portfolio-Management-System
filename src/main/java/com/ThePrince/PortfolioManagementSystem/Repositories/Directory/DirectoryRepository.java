package com.ThePrince.PortfolioManagementSystem.Repositories.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    @Query(value = "SELECT d from Directory d where d.name=:name and d.parentDirectory.id=:parentDirectoryId")
    boolean existsByNameAndParentDirectory(String name, long parentDirectoryId);
}
