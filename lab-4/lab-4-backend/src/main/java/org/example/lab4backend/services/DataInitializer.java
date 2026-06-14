package org.example.lab4backend.services;


import org.example.lab4backend.entities.Permission;
import org.example.lab4backend.entities.Role;
import org.example.lab4backend.entities.User;
import org.example.lab4backend.repositories.PermissionRepository;
import org.example.lab4backend.repositories.RoleRepository;
import org.example.lab4backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.existsByUsername("admin")) return;

        // Permisos
        List<Permission> perms = permissionRepository.saveAll(List.of(
                perm("GET",    "/api/auth/profile"),
                perm("POST",   "/api/auth/logout"),
                perm("GET",    "/api/roles"),
                perm("POST",   "/api/roles"),
                perm("PUT",    "/api/roles/**"),
                perm("GET",    "/api/permissions"),
                perm("GET",    "/api/users"),
                perm("PUT",    "/api/users/**"),
                perm("DELETE", "/api/users/**"),
                perm("GET",    "/api/prestamos"),
                perm("POST",   "/api/prestamos"),
                perm("PUT",    "/api/prestamos/**"),
                perm("GET",    "/api/prestamos/**"),
                perm("POST",   "/api/abonos/**")
        ));

        // Rol ADMIN con todos los permisos
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setActive(true);
        adminRole.setPermissions(perms);
        roleRepository.save(adminRole);

        // Rol USER con permisos básicos
        Role userRole = new Role();
        userRole.setName("USER");
        userRole.setActive(true);
        userRole.setPermissions(List.of(
                perms.get(0),  // GET /api/auth/profile
                perms.get(9),  // GET /api/prestamos
                perms.get(12)  // GET /api/prestamos/**
        ));
        roleRepository.save(userRole);

        // Usuario admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(new BCryptPasswordEncoder().encode("00107223"));
        admin.setName("Administrador");
        admin.setRole(adminRole);
        userRepository.save(admin);

        System.out.println("✅ DataInitializer: admin creado con password '00107223'");
    }

    private Permission perm(String method, String path) {
        Permission p = new Permission();
        p.setMethod(method);
        p.setPath(path);
        return p;
    }
}