package com.ThePrince.PortfolioManagementSystem.DAOs.SubscriberList;

import ch.qos.logback.core.net.ObjectWriter;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Visitor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class SubscriberList {
    @SequenceGenerator(
            name = "sub_seq",
            sequenceName = "sub_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sub_seq"
    )
    private long id;

    @OneToOne
    private Owner owner;

    @OneToMany
    private List<Visitor> subscribers;

}

