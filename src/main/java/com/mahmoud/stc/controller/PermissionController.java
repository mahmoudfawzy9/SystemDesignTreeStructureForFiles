package com.mahmoud.stc.controller;

import com.mahmoud.stc.entity.Permission;
import com.mahmoud.stc.entity.PermissionGroup;
import com.mahmoud.stc.enums.PermissionLevel;
import com.mahmoud.stc.repository.PermissionGroupRepository;
import com.mahmoud.stc.service.PermissionGroupService;
import com.mahmoud.stc.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    private final PermissionGroupService permissionGroupService;

    private final PermissionGroupRepository permissionGroupRepository;

    public PermissionController(PermissionService permissionService, PermissionGroupService permissionGroupService, PermissionGroupRepository permissionGroupRepository) {
        this.permissionService = permissionService;
        this.permissionGroupService = permissionGroupService;
        this.permissionGroupRepository = permissionGroupRepository;
    }

    @PostMapping("/permission-groups")
    public PermissionGroup createPermissionGroup(@RequestParam String groupName) {
        return permissionService.createPermissionGroup(groupName);
    }

    @PostMapping("/permission")
    public Permission createPermission(@RequestParam String userEmail, @RequestParam PermissionLevel permissionLevel, @RequestParam Long permissionGroupId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionGroupId).orElse(null);
        return permissionService.createPermission(userEmail, permissionLevel, permissionGroup);
    }

    @GetMapping("/permission-groups/{permissionGroupId}")
    public PermissionGroup getPermissionGroupById(@PathVariable Long permissionGroupId) {
        return permissionGroupService.getPermissionGroupById(permissionGroupId);
    }

    @DeleteMapping("/permission-groups/{permissionGroupId}")
    public ResponseEntity<?> deletePermissionGroup(@PathVariable Long permissionGroupId) {
        try {
            permissionGroupService.deletePermissionGroup(permissionGroupId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
