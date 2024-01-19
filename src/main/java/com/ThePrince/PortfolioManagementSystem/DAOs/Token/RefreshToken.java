package com.ThePrince.PortfolioManagementSystem.DAOs.Token;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

public class RefreshToken {
    @SequenceGenerator(
            name = "refresh_seq",
            sequenceName = "refresh_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "refresh_seq"
    )
    private long id;
    @Column(nullable = false, unique = false)
    private String refreshToken;
    @Column(nullable = false)
    private Date creationDate;
    @Column(nullable = false)
    private Date expirationDate;
    private boolean expired;
    private boolean revoked;
    @ManyToOne
    private UserEntity userEntity;

}
