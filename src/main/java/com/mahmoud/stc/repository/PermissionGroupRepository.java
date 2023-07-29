package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.Permission;
import com.mahmoud.stc.entity.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {
}
