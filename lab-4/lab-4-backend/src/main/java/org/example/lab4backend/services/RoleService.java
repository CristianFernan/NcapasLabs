package org.example.lab4backend.services;


import org.example.lab4backend.entities.Permission;
import org.example.lab4backend.entities.Role;
import org.example.lab4backend.exceptions.ResourceNotFoundException;
import org.example.lab4backend.repositories.PermissionRepository;
import org.example.lab4backend.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Role> findAll() { return roleRepository.findAll(); }

    public List<Permission> findAllPermissions() { return permissionRepository.findAll(); }

    @Transactional
    public Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        role.setActive(true);
        return roleRepository.save(role);
    }

    @Transactional
    public Role assignPermissions(Integer roleId, List<Integer> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    @Transactional
    public Role toggleActive(Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        role.setActive(!role.getActive());
        return roleRepository.save(role);
    }
}