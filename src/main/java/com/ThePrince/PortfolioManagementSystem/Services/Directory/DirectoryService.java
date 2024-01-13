package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DirectoryService {

    public Directory intializeGenesisDirectory(Owner owner); //creates the first Directory given to a new Portfolio Owner

    public ResponseEntity<Object> createNewDirectory(long parentId, DirectoryDTO directoryDTO , UserDetails userDetails);

    public ResponseEntity<Object> getDirectoryById(long id);

    public ResponseEntity<Object> findDirectoryPath(long id); //returns the directory Path

    public ResponseEntity<Object> moveDirectory(long id, long newParentDirectoryId, UserDetails userDetails);   //returns the new Parent Directory

    public ResponseEntity<Object> deleteDirectoryById(long id, UserDetails userDetails); //returns all the child directories that were deleted with it

    public ResponseEntity<Object> copyDirectory(long id, long parentId, UserDetails userDetails);

    public ResponseEntity<Object> updateDirectory(long id, String name, String description, UserDetails userDetails);
}
