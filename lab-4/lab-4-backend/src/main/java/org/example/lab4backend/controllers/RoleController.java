package org.example.lab4backend.controllers;


import org.example.lab4backend.dto.response.PageableResponse;
import org.example.lab4backend.entities.Permission;
import org.example.lab4backend.repositories.PermissionRepository;
import org.example.lab4backend.entities.Role;
import org.example.lab4backend.exceptions.ResourceNotFoundException;
import org.example.lab4backend.repositories.RoleRepository;
import org.example.lab4backend.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleController(RoleService roleService, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @GetMapping("/roles")
    public ResponseEntity<PageableResponse<Role>> getRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Role> result = roleRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(new PageableResponse<>(result.getContent(), page, size,
                result.getTotalElements()));
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        Role role = roleService.createRole(name);

        if (body.containsKey("permissions")) {
            List<Integer> permissionIds = ((List<?>) body.get("permissions"))
                    .stream()
                    .map(p -> {
                        if (p instanceof Map<?, ?> map) {
                            return Integer.valueOf(map.get("id").toString());
                        }
                        return Integer.valueOf(p.toString());
                    })
                    .toList();
            role = roleService.assignPermissions(role.getId(), permissionIds);
        }

        return ResponseEntity.ok(role);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Integer id,
                                           @RequestBody Map<String, Object> body) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        if (body.containsKey("name")) {
            role.setName((String) body.get("name"));
        }

        if (body.containsKey("active")) {
            role.setActive((Boolean) body.get("active"));
        }

        if (body.containsKey("permissions")) {
            List<Integer> permissionIds = ((List<?>) body.get("permissions"))
                    .stream()
                    .map(p -> {
                        if (p instanceof Map<?, ?> map) {
                            return Integer.valueOf(map.get("id").toString());
                        }
                        return Integer.valueOf(p.toString());
                    })
                    .toList();
            List<Permission> perms = permissionRepository.findAllById(permissionIds);
            role.setPermissions(perms);
        }

        return ResponseEntity.ok(roleRepository.save(role));
    }

    @PutMapping("/roles/{id}/toggle")
    public ResponseEntity<Role> toggleRole(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.toggleActive(id));
    }
}