package com.ThePrince.PortfolioManagementSystem.DAOs.Forum;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Forum {
    @SequenceGenerator(
            name = "forum_seq",
            sequenceName = "forum_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "forum_seq"
    )
    private long id;

    @ManyToOne
    private UserEntity sender;

    @ManyToOne
    private UserEntity receiver;

    @Column(nullable = false)
    private String Subject;

    @Column(nullable = false)
    private String content;
}
