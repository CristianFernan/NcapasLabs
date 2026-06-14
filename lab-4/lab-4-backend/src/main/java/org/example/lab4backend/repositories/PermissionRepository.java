package org.example.lab4backend.repositories;

import org.example.lab4backend.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {}