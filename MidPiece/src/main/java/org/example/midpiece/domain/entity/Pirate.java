package org.example.midpiece.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Pirate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pirate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name =  "pirateName")
    private String name;

    @Column(name =  "bounty")
    private double bounty;

    @Column(name = "pirateCrew")
    private String pirate;

    @Column(name =  "isAlive")
    private boolean isAlive;


}
