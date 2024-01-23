package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.*;
import com.ThePrince.PortfolioManagementSystem.Exceptions.DirectoryExistsException;
import com.ThePrince.PortfolioManagementSystem.Exceptions.ResourceNotFoundException;
import com.ThePrince.PortfolioManagementSystem.Handler.ResponseHandler;
import com.ThePrince.PortfolioManagementSystem.Repositories.Directory.DirectoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DirectoryServiceImpl implements DirectoryService{

    private final DirectoryRepository directoryRepository;

    private final DirectoryPathMapper directoryPathMapper;

    private final DirectoryDTOMapper directoryDTOMapper;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository, DirectoryPathMapper directoryPathMapper, DirectoryDTOMapper directoryDTOMapper) {
        this.directoryRepository = directoryRepository;
        this.directoryPathMapper = directoryPathMapper;
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
    public ResponseEntity<Object> createNewDirectory(long parentId, DirectoryDTO directoryDTO, UserDetails userDetails) {
        if (Objects.isNull(findByNameAndParentDirectory(directoryDTO.name(), parentId))) {
            Directory newDirectory = new Directory(
                    directoryDTO.name(),
                    directoryDTO.description(),
                    directoryRepository.findById(directoryDTO.id()).get().getOwner(),
                    directoryRepository.findById(parentId).get(),
                    new ArrayList<Directory>()
            );
            directoryRepository.save(newDirectory);
            return ResponseHandler.generateResponse(directoryDTOMapper.apply(newDirectory), HttpStatus.OK, new ArrayList<DirectoryPathDTO>());
        } else {
            throw new DirectoryExistsException("There is already a directory here named " + directoryDTO.name());
        }
    }

    @Override
    public ResponseEntity<Object> getDirectoryById(long id) {
        if (directoryRepository.existsById(id)){
            return ResponseHandler.generateResponse(directoryDTOMapper.apply(directoryRepository.findById(id).get()),HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("This directory doesn't exist !!");
        }
    }

    @Override
    public ResponseEntity<Object> findDirectoryPath(long id) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("this Directory doesn't exist in our system"));
        Owner owner = directory.getOwner();
        List<DirectoryPathDTO> directoryPathDTOS = new ArrayList<>();
        directoryPathDTOS.add(directoryPathMapper.apply(directory));
        while (Objects.nonNull(directory.getParentDirectory())){
        }
        return ResponseHandler.generateResponse(directoryPathDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> moveDirectory(long id, long newParentDirectoryId,UserDetails userDetails) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This directory doesn't exist !!"));
        Directory parentDirectory = directoryRepository.findById(newParentDirectoryId).orElseThrow(() -> new ResourceNotFoundException("The new parent repository doesn't exist !!"));
        if (Objects.equals(directory.getOwner(), userDetails)) {
            directory.setParentDirectory(parentDirectory);
            parentDirectory.addChild(directory);
            directoryRepository.save(directory);
            directoryRepository.save(parentDirectory);
            String successMessage = "Your directory's path was changed successfully !";
            return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("This directory doesn't exist !!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteDirectoryById(long id,UserDetails userDetails) {
        Directory directory  = directoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This directory doesn't exist!!"));
        Directory parentDirectory = directory.getParentDirectory();
        List<Directory> children = parentDirectory.getChildren();
        children.remove(directory);
        parentDirectory.setChildren(children);
        directoryRepository.save(parentDirectory);
        int sum = deleteDirectoryByIdRecursive(id,0);
        String successMessage = String.format("Your directory named "+ directory.getName()+" was deleted successfully, and a sum of "+sum + " directories were deleted with it.");
        return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }

    public int deleteDirectoryByIdRecursive(long id, int sum) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This repository doesn't Exist!!"));
        if (!directory.getChildren().isEmpty()){
            for (Directory child : directory.getChildren()){
                sum =+ deleteDirectoryByIdRecursive(child.getId(),sum);
            }
            directoryRepository.deleteById(id);
            return sum;

        } else {
            directoryRepository.deleteById(id);
            return sum+1;
        }
    }

    @Override
    public ResponseEntity<Object> copyDirectory(long id, long parentId, UserDetails userDetails) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This directory doesn't exist !!"));
        Directory newParentDirectory = directoryRepository.findById(parentId).orElseThrow( () -> new ResourceNotFoundException("The new parent directory doesn't exist !!"));
        if ((Objects.equals(newParentDirectory.getOwner().getEmail(), userDetails.getUsername()))) {
            if ((Objects.equals(parentId, directory.getParentDirectory().getId()))) {
                Directory copy = new Directory(
                        directory.getName()+"copy",
                        directory.getDescription(),
                        directory.getOwner(),
                        directory.getParentDirectory(),
                        new ArrayList<Directory>(),
                        directory.isVisible()
                );
                directoryRepository.save(copy);
            } else {
                Directory copy = new Directory(
                        directory.getName(),
                        directory.getDescription(),
                        directory.getOwner(),
                        newParentDirectory,
                        new ArrayList<Directory>(),
                        directory.isVisible()
                );
                directoryRepository.save(copy);
            }
            Directory savedCopy = findByNameAndParentDirectory(directory.getName(), newParentDirectory.getId());
            savedCopy.setChildren(copyChildrenDirectoriesRecursive(directory));
            String successMessage = String.format("Your repository named " + savedCopy.getName()+" was copied successfully into " +directory.getParentDirectory().getName()+" !!");
            return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse("Act was not successful !!", HttpStatus.FORBIDDEN);
        }
    }

    public List<Directory> copyChildrenDirectoriesRecursive(Directory parent){
        List<Directory> children = parent.getChildren();
        List<Directory> newChildren = new ArrayList<>();
        if (!parent.getChildren().isEmpty()) {
            for (Directory child : children) {
                newChildren.add(new Directory(
                        child.getName(),
                        child.getDescription(),
                        child.getOwner(),
                        parent,
                        copyChildrenDirectoriesRecursive(child)
                ));
            }
            return newChildren;
        } else {
            return newChildren;
        }
    }

    @Override
    public ResponseEntity<Object> updateDirectory(DirectoryUpdater directoryUpdater, UserDetails userDetails) {
        Directory directory = directoryRepository.findById(directoryUpdater.id()).orElseThrow(() -> new ResourceNotFoundException("This directory doesn't exist !!"));
        if (Objects.equals(directory.getOwner(), userDetails)){
            directory.setName(directoryUpdater.name());
            directory.setDescription(directoryUpdater.description());
            directory.setVisible(directoryUpdater.isVisible());
            directoryRepository.save(directory);
            String successMessage = String.format("Directory Updated Successfully");
            return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
        } else {
            throw (new ResourceNotFoundException("This directory doesn't exist !!"));
        }
    }

    @Override
    public ResponseEntity<Object> createNewTestDirectory(DirectoryDTO directoryDTO) {
        directoryRepository.save(new Directory(
                directoryDTO.name(),
                directoryDTO.description(),
                null,
                null,
                new ArrayList<Directory>()
        ));
        return ResponseHandler.generateResponse("Created Successfully", HttpStatus.OK);
    }

    public Directory findByNameAndParentDirectory(String name, long parentId){
        return directoryRepository.findByNameAndParentDirectory(name,parentId);
    }
}
