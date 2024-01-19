package com.ThePrince.PortfolioManagementSystem.Repositories.UserEntity;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query(value = "select u from UserEntity u where u.role.name=:role")
    List<UserEntity> findByRolePaged(@Param("role") String role, Pageable pageable);
    @Query(value = "select count(u) from UserEntity u where u.role.name=:role")
    int getCountByRole(@Param("role") String role);
    @Query(value = "select u from UserEntity u where u.role.name='VISITOR' or u.role.name='OWNER' ")
    List<UserEntity> getAllPaged(Pageable pageable);
    @Query(value = "select count(u) from UserEntity u where u.role.name='VISITOR' or u.role.name='OWNER' ")
    int getCountPaged();

    @Query(value = "select u from UserEntity u where u.email=:email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

}
