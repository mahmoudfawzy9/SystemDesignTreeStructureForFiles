package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.entity.PermissionGroup;
import com.mahmoud.stc.exception.PermissionGroupNotFoundException;
import com.mahmoud.stc.helper.message.BusinessMessage;
import com.mahmoud.stc.repository.PermissionGroupRepository;
import com.mahmoud.stc.service.PermissionGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@Transactional
public class PermissionGroupServiceImpl implements PermissionGroupService {

    @Autowired
    PermissionGroupRepository permissionGroupRepository;

    @Override
    public PermissionGroup createPermissionGroup(PermissionGroup permissionGroup) {
        return permissionGroupRepository.save(permissionGroup);
    }

    @Override
    public PermissionGroup getPermissionGroupById(Long permissionGroupId) {
        return permissionGroupRepository.findById(permissionGroupId)
                .orElseThrow(() -> new PermissionGroupNotFoundException(BusinessMessage.PermissionGroup.PERMISSION_GROUP_NOT_FOUND));
    }

    @Override
    public void deletePermissionGroup(Long permissionId) {
        PermissionGroup permissionGroup = permissionGroupRepository.findById(permissionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        permissionGroupRepository.delete(permissionGroup);
        log.info("Permission group is deleted successfully");
    }
}
