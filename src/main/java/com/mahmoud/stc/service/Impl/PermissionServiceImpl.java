package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.entity.Permission;
import com.mahmoud.stc.entity.PermissionGroup;
import com.mahmoud.stc.enums.Role;
import com.mahmoud.stc.exception.PermissionNotFoundException;
import com.mahmoud.stc.repository.PermissionGroupRepository;
import com.mahmoud.stc.repository.PermissionRepository;
import com.mahmoud.stc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionGroupRepository permissionGroupRepository;
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionGroupRepository permissionGroupRepository, PermissionRepository permissionRepository) {
        this.permissionGroupRepository = permissionGroupRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public PermissionGroup createPermissionGroup(String groupName) {
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setGroupName(groupName);
        permissionGroupRepository.save(permissionGroup);
        return permissionGroup;
    }

    @Override
    public Permission createPermission(String userEmail, Role permissionLevel, PermissionGroup permissionGroup) {
        Permission permission = new Permission();
        permission.setUserEmail(userEmail);
        permission.setPermissionLevel(permissionLevel);
        permission.setPermissionGroup(permissionGroup);
        permissionRepository.save(permission);
        return permission;
    }
}
