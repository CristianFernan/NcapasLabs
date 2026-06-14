package org.example.lab4backend.controllers;


import org.example.lab4backend.dto.response.PageableResponse;
import org.example.lab4backend.entities.Role;
import org.example.lab4backend.entities.User;
import org.example.lab4backend.exceptions.BadRequestException;
import org.example.lab4backend.exceptions.ResourceNotFoundException;
import org.example.lab4backend.repositories.RoleRepository;
import org.example.lab4backend.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<PageableResponse<User>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<User> result = userRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(new PageableResponse<>(result.getContent(), page, size,
                result.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody Map<String, Object> body) {
        if (userRepository.existsByUsername((String) body.get("username"))) {
            throw new BadRequestException("El username ya existe");
        }

        Role role = roleRepository.findById(
                        Integer.valueOf(body.get("role").toString()))
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        User user = new User();
        user.setUsername((String) body.get("username"));
        user.setPassword(new BCryptPasswordEncoder().encode((String) body.get("password")));
        user.setName((String) body.get("name") + " " + body.get("surname"));
        user.setRole(role);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PutMapping("/{id}/block")
    public ResponseEntity<?> toggleBlock(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setBlocked(!user.isBlocked());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeRole(@PathVariable Integer id,
                                        @RequestBody Map<String, Integer> body) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setRole(roleRepository.findById(body.get("roleId"))
                .orElseThrow(() -> new RuntimeException("Rol no encontrado")));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}