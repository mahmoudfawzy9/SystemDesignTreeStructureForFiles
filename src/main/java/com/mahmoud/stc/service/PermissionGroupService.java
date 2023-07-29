package com.mahmoud.stc.service;

import com.mahmoud.stc.entity.PermissionGroup;

public interface PermissionGroupService {

    public PermissionGroup createPermissionGroup(PermissionGroup permissionGroup);

    public PermissionGroup getPermissionGroupById(Long permissionGroupId);

    public void deletePermissionGroup(Long permissionId);

}
