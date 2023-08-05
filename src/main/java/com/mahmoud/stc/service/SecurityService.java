package com.mahmoud.stc.service;

import com.mahmoud.stc.config.UserAuthenticationData;
import com.mahmoud.stc.entity.BaseUserEntity;
import com.mahmoud.stc.entity.UserEntity;
import com.mahmoud.stc.entity.UserToken;
import com.mahmoud.stc.enums.Role;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Optional;
import java.util.Set;

import static com.mahmoud.stc.cache.Caches.USERS_BY_TOKENS;

public interface SecurityService {

    Optional<UserAuthenticationData> findUserDetailsByAuthToken(String token);

    @CacheEvict(cacheNames = {USERS_BY_TOKENS}, key = "#userToken.token")
    UserToken extendUserExpirationTokenIfNeeded(UserToken userToken);


    BaseUserEntity getCurrentUser();

    Optional<BaseUserEntity> getCurrentUserOptional();

    Long getCurrentUserFileId(UserEntity userId);

    Boolean userHasRole(BaseUserEntity user, Role role);
  
    Boolean userHasRole(Role role);
  
    Boolean currentUserHasRole(Role role);

    boolean currentUserHasMaxRoleLevelOf(Role role);

    Set<Role> getCurrentUserRoles();

}
