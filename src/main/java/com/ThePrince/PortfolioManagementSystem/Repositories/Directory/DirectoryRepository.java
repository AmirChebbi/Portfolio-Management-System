package com.ThePrince.PortfolioManagementSystem.Repositories.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Transactional
@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    @Query(value = "SELECT d from Directory d where d.name=:name and d.parentDirectory.id=:parentDirectoryId")
    Directory findByNameAndParentDirectory(@Param("name") String name, @Param("parentDirectoryId") long parentDirectoryId);

    @Query(value = "SELECT d from Directory d where d.id=:id and d.owner.email=:email")
    Optional<Directory> findByIdAndOwnerEmail(@Param("id") long id,@Param("email") String email);

    @Query(value = "SELECT count(d) from Directory d where d.owner.email=:email")
    int getUserDirectoryCount(String username);
}
