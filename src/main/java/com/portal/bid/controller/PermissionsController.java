package com.portal.bid.controller;

import com.portal.bid.entity.Permissions;
import com.portal.bid.service.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionsController {

    private final PermissionsService permissionsService;

    @Autowired
    public PermissionsController(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    @GetMapping
    public ResponseEntity<List<Permissions>> getAllPermissions() {
        List<Permissions> permissions = permissionsService.getAllPermissions();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permissions> getPermissionById(@PathVariable int id) {
        Optional<Permissions> permission = permissionsService.getPermissionById(id);
        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Permissions> createPermission(@RequestBody Permissions permission) {
        Permissions createdPermission = permissionsService.createPermission(permission);
        return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permissions> updatePermission(@PathVariable int id, @RequestBody Permissions permission) {
        Optional<Permissions> existingPermissionOpt = permissionsService.getPermissionById(id);

        if (existingPermissionOpt.isPresent()) {
            Permissions existingPermission = existingPermissionOpt.get();

            // Update only the fields that are not null
            if (permission.getPermission_name() != null) {
                existingPermission.setPermission_name(permission.getPermission_name());
            }
            if (permission.getCreatedBy() != null) {
                existingPermission.setCreatedBy(permission.getCreatedBy());
            }
            if (permission.getUpdatedBy() != null) {
                existingPermission.setUpdatedBy(permission.getUpdatedBy());
            }
            // Note: `createdAt` and `updatedAt` are managed automatically by the entity lifecycle methods

            // Save the updated entity
            Permissions updatedPermission = permissionsService.createPermission(existingPermission);
            return ResponseEntity.ok(updatedPermission);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable int id) {
        boolean deleted = permissionsService.deletePermission(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
