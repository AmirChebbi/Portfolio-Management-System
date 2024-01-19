package com.ThePrince.PortfolioManagementSystem.DAOs.Directory;

import com.ThePrince.PortfolioManagementSystem.DAOs.UserEntity.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Directory {

    @SequenceGenerator(
            name = "direct_sequence",
            sequenceName = "direct_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "direct_sequence"
    )
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "id")
    private Directory parentDirectory;

    @OneToMany
    private List<Directory> children;

    @Column(nullable = false)
    private boolean isVisible;

    public Directory(String name, String description, Owner owner, Directory parentDirectory, List<Directory> children, boolean isVisible) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.parentDirectory = parentDirectory;
        this.children = children;
        this.isVisible = isVisible;
    }

    public Directory(String name, String description, Owner owner, Directory parentDirectory, List<Directory> children) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.parentDirectory = parentDirectory;
        this.children = children;
    }
    public void addChild(Directory directory){
        this.children.add(directory);
    }

}
