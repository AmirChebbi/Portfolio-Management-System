package com.ThePrince.PortfolioManagementSystem.DAOs.File;

import com.ThePrince.PortfolioManagementSystem.DAOs.Directory.Directory;
import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.DirectColorModel;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class File {
    @SequenceGenerator(
            name = "file_seq",
            sequenceName = "file_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_seq"
    )
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private String filePath;

    @Column
    private float overallRating;

    @ManyToOne
    private Directory directory;

    @ManyToOne
    private Owner owner;

}

