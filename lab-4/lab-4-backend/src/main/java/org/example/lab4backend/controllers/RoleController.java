package org.example.lab4backend.controllers;


import org.example.lab4backend.entities.Permission;
import org.example.lab4backend.entities.Role;
import org.example.lab4backend.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(roleService.createRole(body.get("name")));
    }

    @PutMapping("/roles/{id}/permissions")
    public ResponseEntity<Role> assignPermissions(@PathVariable Integer id,
                                                  @RequestBody Map<String, List<Integer>> body) {
        return ResponseEntity.ok(roleService.assignPermissions(id, body.get("permissions")));
    }

    @PutMapping("/roles/{id}/toggle")
    public ResponseEntity<Role> toggleRole(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.toggleActive(id));
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> getPermissions() {
        return ResponseEntity.ok(roleService.findAllPermissions());
    }
}