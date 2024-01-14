package com.ThePrince.PortfolioManagementSystem.Controllers.Directory;


import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.Services.Directory.DirectoryService;
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

    @PostMapping("/createtest")
    public ResponseEntity<Object> createNewTestDirectory(@RequestBody DirectoryDTO directoryDTO){
        return directoryService.createNewTestDirectory(directoryDTO);
    }

    @PostMapping("/create/{parentId}")
    public ResponseEntity<Object> createNewDirectory(@PathVariable long parentId, @RequestBody DirectoryDTO directoryDTO, @AuthenticationPrincipal UserDetails userDetails ){
        return directoryService.createNewDirectory(parentId,directoryDTO,userDetails);
    }

    @GetMapping("/get_by_id/{id}")
    public ResponseEntity<Object> getDirectoryById(@PathVariable long id){
        return directoryService.getDirectoryById(id);
    }

    @GetMapping("/find_path/{id}")
    public ResponseEntity<Object> findDirectoryPath(long id){
        return directoryService.findDirectoryPath(id);
    }

    @PutMapping("/move/{id}")
    public ResponseEntity<Object> moveDirectory(@PathVariable long id, @RequestParam long newParentDirectoryId, @AuthenticationPrincipal UserDetails userDetails) {
        return directoryService.moveDirectory(id, newParentDirectoryId, userDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDirectoryById(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails){
        return directoryService.deleteDirectoryById(id, userDetails);
    }

    @PostMapping("/copy/{id}")
    public ResponseEntity<Object> copyDirectory(@PathVariable long id, @RequestParam long parentId, @AuthenticationPrincipal UserDetails userDetails){
        return directoryService.copyDirectory(id, parentId, userDetails);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> updateDirectory(@PathVariable long id, @RequestParam String name,@RequestParam String description, @AuthenticationPrincipal UserDetails userDetails){
        return directoryService.updateDirectory(id, name, description, userDetails);
    }


}
