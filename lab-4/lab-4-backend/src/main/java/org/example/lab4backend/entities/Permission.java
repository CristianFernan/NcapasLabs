package org.example.lab4backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String method;   // GET, POST, PUT, DELETE

    @Column(nullable = false)
    private String path;     // /api/prestamos, /api/prestamos/**
}