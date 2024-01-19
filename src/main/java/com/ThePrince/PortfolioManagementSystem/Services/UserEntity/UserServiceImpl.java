package com.ThePrince.PortfolioManagementSystem.Services.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import com.ThePrince.PortfolioManagementSystem.DTOs.UserEntity.UserDTOMapper;
import com.ThePrince.PortfolioManagementSystem.Exceptions.RessourceNotFoundException;
import com.ThePrince.PortfolioManagementSystem.Handler.ResponseHandler;
import com.ThePrince.PortfolioManagementSystem.Repositories.Role.RoleRepository;
import com.ThePrince.PortfolioManagementSystem.Repositories.SubscriberList.FollowListRepository;
import com.ThePrince.PortfolioManagementSystem.Repositories.UserEntity.UserRepository;
import jakarta.jws.soap.SOAPBinding;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final RoleRepository roleRepository;
    private final FollowListRepository followListRepository;

    public UserServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper, RoleRepository roleRepository, FollowListRepository followListRepository) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.roleRepository = roleRepository;
        this.followListRepository = followListRepository;
    }

    @Override
    public ResponseEntity<Object> getUserById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RessourceNotFoundException("User Doesn't exist !!"));
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

    @Override
    public ResponseEntity<Object> viewProfile(UserDetails userDetails) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new RessourceNotFoundException("Who tf Are you ??"));
        if(Objects.equals(user.getRole().getName(),"OWNER")){
            int subCount = followListRepository.getFollowCount(userDetails.getUsername());

        }
    }

    @Override
    public ResponseEntity<Object> enableOrDisableUser(UUID id, boolean enable) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("User doesn't exist!!"));
        user.setEnabled(enable);
        userRepository.save(user);
        String successMessage = String.format("User account was set to "+ enable+" successfully");
        return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> enableById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(()-> new RessourceNotFoundException("User doesn't exist!!"));
        user.setEnabled(true);
        userRepository.save(user);
        String successMessage = String.format("User account was enabled successfully");
        return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RessourceNotFoundException("User doesn't exist !!"));
    }

    @Override
    public boolean isEmailRegistered(String email) {
        if (userRepository.findByEmail(email).isEmpty()){
            return false;
        } else {
            return true;
        }
    }
}
