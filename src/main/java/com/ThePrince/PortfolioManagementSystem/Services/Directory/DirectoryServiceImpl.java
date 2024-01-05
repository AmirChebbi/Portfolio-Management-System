package com.ThePrince.PortfolioManagementSystem.Services.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryDTOMapper;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathMapper;
import com.ThePrince.PortfolioManagementSystem.Exceptions.DirectoryExistsException;
import com.ThePrince.PortfolioManagementSystem.Exceptions.RessourceNotFoundException;
import com.ThePrince.PortfolioManagementSystem.Handler.ResponseHandler;
import com.ThePrince.PortfolioManagementSystem.Repositories.Directory.DirectoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public ResponseEntity<Object> createNewDirectory(Owner owner, DirectoryPathDTO parentDirectory, DirectoryDTO directoryDTO) {
        if (!directoryRepository.existsByNameAndParentDirectory(directoryDTO.name(), parentDirectory.id())) {
            Directory newDirectory = new Directory(
                    directoryDTO.name(),
                    directoryDTO.description(),
                    owner,
                    directoryRepository.findById(parentDirectory.id()).get(),
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
            throw new  RessourceNotFoundException("This directory doesn't exist !!");
        }
    }

    @Override
    public ResponseEntity<Object> findDirectoryPath(long id,List<DirectoryPathDTO> directoryPathDTOS) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("this Directory doesn't exist in our system"));
        directoryPathDTOS.add(directoryPathMapper.apply(directory));
        if (directory.getChildren() == null){
            return ResponseHandler.generateResponse(directoryPathDTOS.reversed(), HttpStatus.OK);
        } else {
            return findDirectoryPath(directory.getParentDirectory().getId(),directoryPathDTOS);
        }
    }

    @Override
    public ResponseEntity<Object> moveDirectory(long id, long newParentDirectoryId) {
            Directory directory = directoryRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("This directory doesn't exist !!"));
            Directory parentDirectory = directoryRepository.findById(newParentDirectoryId).orElseThrow(() -> new RessourceNotFoundException("The new parent repository doesn't exist !!") );
            directory.setParentDirectory(parentDirectory);
            directoryRepository.save(directory);
            String successMessage = "Your directory's path was changed successfully !";
            return ResponseHandler.generateResponse(successMessage, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteDirectoryById(long id) {
        directoryRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("This directory doesn't exist!!"));
        List<DirectoryPathDTO> list = deleteDirectoryByIdRecursive(id);
        return ResponseHandler.generateResponse(list, HttpStatus.OK);
    }

    public List<DirectoryPathDTO> deleteDirectoryByIdRecursive(long id) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("This repository doesn't Exist!!"));
        List<DirectoryPathDTO> list = new ArrayList<>();
        if (!directory.getChildren().isEmpty()){
            for (Directory child : directory.getChildren()){
                 list.addAll(deleteDirectoryByIdRecursive(child.getId()));
            }
            list.add(directoryPathMapper.apply(directory));
            directoryRepository.deleteById(id);
            return list;
        } else {
            DirectoryPathDTO directoryPathDTO = directoryPathMapper.apply(directory);
            directoryRepository.deleteById(id);
            list.add(directoryPathDTO);
            return list;
        }
    }

    @Override
    public ResponseEntity<Object> copyDirectory(long id, long parentId) {
        return null;
    }

    @Override
    public ResponseEntity<Object> updateDirectory(long id, DirectoryDTO directoryDTO) {
        return null;
    }

    public boolean existsByNameAndParentDirectory(String name, DirectoryPathDTO parentDirectory){
        return directoryRepository.existsByNameAndParentDirectory(name,parentDirectory.id());
    }

}
