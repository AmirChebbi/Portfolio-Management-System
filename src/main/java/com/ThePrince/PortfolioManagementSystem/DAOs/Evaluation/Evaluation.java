package com.ThePrince.PortfolioManagementSystem.DAOs.Evaluation;

import com.ThePrince.PortfolioManagementSystem.DAOs.File.File;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Visitor;
import jakarta.persistence.*;
import jdk.jfr.MemoryAddress;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Evaluation {

    @SequenceGenerator(
            name = "eval_seq",
            sequenceName = "eval_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "eval_seq"
    )
    private long id;

    @ManyToOne
    private Visitor viewer;


    @ManyToOne
    private File file;

    @Column
    private String comment;

    @Column(nullable = false)
    private int rating;

}
