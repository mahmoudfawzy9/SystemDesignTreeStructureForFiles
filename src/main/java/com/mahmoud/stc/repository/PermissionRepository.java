package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.Permission;
import com.mahmoud.stc.enums.PermissionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
      Optional<Permission> findByPermissionLevel(PermissionLevel PermissionLevel);

      boolean existsById(PermissionLevel permissionLevel);
}
