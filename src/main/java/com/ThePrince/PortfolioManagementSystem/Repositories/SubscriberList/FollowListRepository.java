package com.ThePrince.PortfolioManagementSystem.Repositories.SubscriberList;

import com.ThePrince.PortfolioManagementSystem.DAOs.FollowList.FollowList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Transactional
@Repository
public interface FollowListRepository extends JpaRepository<FollowList, Long> {
    @Query(value = "select count(f) from FollowList f where f.owner.email=:email")
    int getFollowerCount(@Param("email") String email);
    @Query(value = "select count(f) from FollowList f, UserEntity u where =:email")
    Integer getFollowingCount(String username);
}
