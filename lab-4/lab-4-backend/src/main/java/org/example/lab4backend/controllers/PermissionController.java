package org.example.lab4backend.controllers;

import org.example.lab4backend.dto.response.PageableResponse;
import org.example.lab4backend.entities.Permission;
import org.example.lab4backend.repositories.PermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionRepository permissionRepository;

    public PermissionController(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @GetMapping
    public ResponseEntity<PageableResponse<Permission>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Permission> result = permissionRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(new PageableResponse<>(result.getContent(), page, size,
                result.getTotalElements()));
    }


    @PostMapping
    public ResponseEntity<Permission> create(@RequestBody Map<String, String> body) {
        Permission p = new Permission();
        p.setMethod(body.get("method"));
        p.setPath(body.get("path"));
        return ResponseEntity.ok(permissionRepository.save(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        permissionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}