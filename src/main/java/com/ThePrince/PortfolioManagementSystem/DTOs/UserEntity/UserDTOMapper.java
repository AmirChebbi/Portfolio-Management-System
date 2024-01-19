package com.ThePrince.PortfolioManagementSystem.DTOs.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class UserDTOMapper implements Function<UserEntity, UserDTO> {
    @Override
    public UserDTO apply(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getFistName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getPhoneNumber()
        );
    }
}
