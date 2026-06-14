package org.example.lab4backend.controllers;

import jakarta.validation.Valid;
import org.example.lab4backend.config.JsonWebToken;
import org.example.lab4backend.dto.request.LoginRequest;
import org.example.lab4backend.dto.request.SignupRequest;
import org.example.lab4backend.dto.response.AuthResponse;
import org.example.lab4backend.entities.Role;
import org.example.lab4backend.entities.User;
import org.example.lab4backend.exceptions.BadRequestException;
import org.example.lab4backend.repositories.RoleRepository;
import org.example.lab4backend.repositories.UserRepository;
import org.example.lab4backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JsonWebToken jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, UserRepository userRepository,
                          RoleRepository roleRepository, JsonWebToken jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername());
        if (user == null || !encoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        if (user.isBlocked()) return ResponseEntity.status(403).body("Cuenta bloqueada");
        String token = jwtUtil.createToken(user);
        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(),
                user.getName(), user.getRole().getName()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new BadRequestException("El username ya existe");
        }
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setRole(userRole);
        userRepository.save(user);
        String token = jwtUtil.createToken(user);
        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(),
                user.getName(), user.getRole().getName()));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}