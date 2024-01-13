package com.ThePrince.PortfolioManagementSystem.Controllers.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import com.ThePrince.PortfolioManagementSystem.Services.Directory.DirectoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/directory")
public class DirectoryController {
    private final DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createNewDirectory(@RequestBody long parentId, @RequestBody DirectoryDTO directoryDTO, @AuthenticationPrincipal UserDetails userDetails ){
        return directoryService.createNewDirectory(parentId,directoryDTO,userDetails);
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Object> getDirectoryById(@PathVariable long id){
        return directoryService.getDirectoryById(id);
    }

    @GetMapping("/find_path/{id}")
    public ResponseEntity<Object> findDirectoryPath(long id, List<DirectoryPathDTO> directoryPathDTOS); //returns the directory Path

    public ResponseEntity<Object> moveDirectory(long id, long newParentDirectoryId);   //returns the new Parent Directory

    public ResponseEntity<Object> deleteDirectoryById(long id); //returns all the child directories that were deleted with it

    public ResponseEntity<Object> copyDirectory(long id, long parentId, @AuthenticationPrincipal UserDetails userDetails);

    public ResponseEntity<Object> updateDirectory(long id, String name, String description, @AuthenticationPrincipal UserDetails userDetails);


}
