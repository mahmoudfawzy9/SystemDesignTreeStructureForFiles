package com.mahmoud.stc.service;

import com.mahmoud.stc.entity.Permission;
import com.mahmoud.stc.entity.PermissionGroup;
import com.mahmoud.stc.enums.Role;

public interface PermissionService {

    PermissionGroup createPermissionGroup(String groupName);

    Permission createPermission(String userEmail, Role permissionLevel, PermissionGroup permissionGroup);

}
