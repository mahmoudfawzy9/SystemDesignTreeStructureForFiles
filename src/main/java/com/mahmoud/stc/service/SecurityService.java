package com.mahmoud.stc.service;

import com.mahmoud.stc.config.UserAuthenticationData;
import com.mahmoud.stc.entity.BaseUserEntity;
import com.mahmoud.stc.entity.UserEntity;
import com.mahmoud.stc.enums.Role;

import java.util.Optional;
import java.util.Set;

public interface SecurityService {

    Optional<UserAuthenticationData> findUserDetailsByAuthToken(String token);

    BaseUserEntity getCurrentUser();

    Long getCurrentUserFileId(UserEntity userId);

    Boolean userHasRole(BaseUserEntity user, Role role);
  
    Boolean userHasRole(Role role);
  
    Boolean currentUserHasRole(Role role);

    Optional<BaseUserEntity> getCurrentUserOptional();

    boolean currentUserHasMaxRoleLevelOf(Role role);

    Set<Role> getCurrentUserRoles();

}
