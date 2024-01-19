package com.ThePrince.PortfolioManagementSystem.DAOs.Token;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import jakarta.persistence.*;

import java.util.Date;

public class ConfirmationToken {
    @SequenceGenerator(
            name = "confirmation_seq",
            sequenceName = "confirmation_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_seq"
    )
    private long id;
    @Column(nullable = false, unique = true)
    private String confirmationToken;
    private Date creationDate;
    private Date expirationDate;
    private Date confirmationDate;
    @ManyToOne
    private UserEntity userEntity;
    public ConfirmationToken(String confirmationToken, Date creationDate, Date expirationDate, Date confirmationDate, UserEntity userEntity) {
        this.confirmationToken = confirmationToken;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.confirmationDate = confirmationDate;
        this.userEntity = userEntity;
    }
}
