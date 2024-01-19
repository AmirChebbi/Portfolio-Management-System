package com.ThePrince.PortfolioManagementSystem.DAOs.NewsLettre;

import com.ThePrince.PortfolioManagementSystem.DAOs.FollowList.SubscriberList;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
@Table
public class NewsLetter {
    @SequenceGenerator(
            name = "news_sequence",
            sequenceName = "news_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "news_sequence"
    )
    private long id;

    @ManyToOne
    private Owner sender;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;

    @OneToOne
    private SubscriberList subscriberList;
}
