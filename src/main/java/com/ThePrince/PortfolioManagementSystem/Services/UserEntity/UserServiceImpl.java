package com.ThePrince.PortfolioManagementSystem.Services.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.Evaluation.Evaluation;
import com.ThePrince.PortfolioManagementSystem.DAOs.File.File;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import com.ThePrince.PortfolioManagementSystem.DTOs.UserEntity.UserDTOMapper;
import com.ThePrince.PortfolioManagementSystem.Exceptions.ResourceNotFoundException;
import com.ThePrince.PortfolioManagementSystem.Handler.ResponseHandler;
import com.ThePrince.PortfolioManagementSystem.Repositories.Directory.DirectoryRepository;
import com.ThePrince.PortfolioManagementSystem.Repositories.Role.RoleRepository;
import com.ThePrince.PortfolioManagementSystem.Repositories.FollowList.FollowListRepository;
import com.ThePrince.PortfolioManagementSystem.Repositories.UserEntity.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleRepository roleRepository;
    private final FollowListRepository followListRepository;
    private final DirectoryRepository directoryRepository;

    public UserServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper, RoleRepository roleRepository, FollowListRepository followListRepository, DirectoryRepository directoryRepository) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.roleRepository = roleRepository;
        this.followListRepository = followListRepository;
        this.directoryRepository = directoryRepository;
    }

    @Override
    public ResponseEntity<Object> getUserById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Doesn't exist !!"));
        return ResponseHandler.generateResponse(userDTOMapper.apply(user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllByRole(long pageNumber, String role) {
        final Pageable pageable = PageRequest.of((int) pageNumber -1, 10 );
        List<UserEntity> userEntities = userRepository.findByRolePaged(role, pageable);
        if (userEntities.isEmpty() && pageNumber>1 ){
            return getAllByRole(1,role);
        }
        return ResponseHandler.generateResponse(userEntities,HttpStatus.OK,userEntities.size(),userRepository.getCountByRole(role));
    }

    @Override
    public ResponseEntity<Object> getAll(long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber -1, 10 );
        List<UserEntity> userEntities = userRepository.getAllPaged(pageable);
        if (userEntities.isEmpty() && pageNumber>1 ){
            return getAll(1);
        }
        return ResponseHandler.generateResponse(userEntities,HttpStatus.OK,userEntities.size(),userRepository.getCountPaged());
    }

    //Method still under construction due to the fact that the work, forum and eval
    //related Classes are not yet ready, but so I don't get an error while testing :}
    @Override
    public ResponseEntity<Object> viewProfile(UserDetails userDetails) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new ResourceNotFoundException("Who tf Are you ??"));
        if(Objects.equals(user.getRole().getName(),"OWNER")){
            HashMap<String, Integer> countMap = new HashMap<>() ;
            countMap.put("followCount", followListRepository.getFollowerCount(userDetails.getUsername()));
            countMap.put("directoryCount", directoryRepository.getUserDirectoryCount(userDetails.getUsername()));
//            int evalCount
//            int forumCount
//            int workCount
            List<File> topFiles = new ArrayList<>();
            return ResponseHandler.generateProfileResponse(countMap, Collections.singletonList(topFiles),HttpStatus.OK);
        } else if (Objects.equals(user.getRole().getName(),"VISITOR")){
            HashMap<String, Integer> countMap = new HashMap<>() ;
            countMap.put("followCount", followListRepository.getFollowingCount(userDetails.getUsername()));
            countMap.put("directoryCount", directoryRepository.getUserDirectoryCount(userDetails.getUsername()));
//            int evalCount
//            int forumCount
            List<Evaluation> lastTraffic = new ArrayList<>();
            return ResponseHandler.generateProfileResponse(countMap, Collections.singletonList(lastTraffic),HttpStatus.OK);
        } else {
            return null; //Will become the Admin Dashboard later.
        }
    }

    @Override
    public ResponseEntity<Object> enableOrDisableUser(UUID id, boolean enable) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User doesn't exist!!"));
        user.setEnabled(enable);
        userRepository.save(user);
        String successMessage = String.format("User account was set to "+ enable+" successfully");
        return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> enableById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User doesn't exist!!"));
        user.setEnabled(true);
        userRepository.save(user);
        String successMessage = "User account was enabled successfully";
        return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist !!"));
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
