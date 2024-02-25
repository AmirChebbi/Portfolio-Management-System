package com.ThePrince.PortfolioManagementSystem.Services.Authentication;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.Role.Role;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Visitor;
import com.ThePrince.PortfolioManagementSystem.DTOs.Authentication.LoginDTO;
import com.ThePrince.PortfolioManagementSystem.DTOs.Authentication.RegistrationDTO;
import com.ThePrince.PortfolioManagementSystem.Exceptions.ResourceNotFoundException;
import com.ThePrince.PortfolioManagementSystem.Exceptions.UnauthorizedActionException;
import com.ThePrince.PortfolioManagementSystem.Handler.ResponseHandler;
import com.ThePrince.PortfolioManagementSystem.Repositories.Role.RoleRepository;
import com.ThePrince.PortfolioManagementSystem.Repositories.UserEntity.UserRepository;
import com.ThePrince.PortfolioManagementSystem.Services.Directory.DirectoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DirectoryService directoryService;

    public AuthenticationServiceImpl(RoleRepository roleRepository, UserRepository userRepository, DirectoryService directoryService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.directoryService = directoryService;
    }

    @Override
    public ResponseEntity<Object> register(RegistrationDTO registrationDTO) {
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()){
            throw new UnauthorizedActionException("Email already taken");
        } else if (userRepository.findByPhoneNumber(registrationDTO.getPhoneNumber())) {
            throw new UnauthorizedActionException("Phone Number already taken");
        }
        UserEntity user = new UserEntity(
                registrationDTO.getFirstName(),
                registrationDTO.getLastName(),
                registrationDTO.getEmail(),
                registrationDTO.getPassword(),
                findRoleByName(registrationDTO.getRole()),
                registrationDTO.getPhoneNumber()
        );
        if (registrationDTO.getRole().equals("Owner")){
            Owner owner= (Owner) user;
            Directory directory =directoryService.intializeGenesisDirectory(owner);
            owner.setGenesisDirectory(directory);
            userRepository.save(owner);
        }else {
            userRepository.save((Visitor) user);
        }
        return ResponseHandler.generateResponse("vj", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Object> confirmation(String ConfirmationToken) {
        return null;
    }

    @Override
    public ResponseEntity<Object> disableAccount(String token) {
        return null;
    }

    @Override
    public void refresh(String token) {

    }

    @Override
    public ResponseEntity<Object> logout(String token) {
        return null;
    }

    private Role findRoleByName(String name){
        return roleRepository.getRoleByName(name).orElseThrow(()-> new ResourceNotFoundException("Role doesn't exist!!"));
    }

}
