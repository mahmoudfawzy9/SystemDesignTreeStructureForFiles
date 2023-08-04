package com.mahmoud.stc.service.Impl;

import com.mahmoud.stc.config.UserAuthenticationData;
import com.mahmoud.stc.entity.BaseUserEntity;
import com.mahmoud.stc.entity.UserEntity;
import com.mahmoud.stc.entity.UserToken;
import com.mahmoud.stc.enums.Role;
import com.mahmoud.stc.repository.CommonUserRepository;
import com.mahmoud.stc.repository.FileRepository;
import com.mahmoud.stc.repository.UserTokenRepository;
import com.mahmoud.stc.service.SecurityService;
import com.mahmoud.stc.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.mahmoud.stc.utils.EntityConstants.AUTH_TOKEN_VALIDITY;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.Authentication;

public class SecurityServiceImpl  implements SecurityService {

    @Autowired
    UserTokenRepository userTokenRepository;

    @Autowired
    FileRepository userFileRepository;

    @Autowired
    private CommonUserRepository userRepo;

    public SecurityServiceImpl(CommonUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<UserAuthenticationData> findUserDetailsByAuthToken(String token) {
        return ofNullable(token)
                .flatMap(this::getUserByAuthenticationToken)
                .map(this::createUserAuthData);
    }


    @Override
    public BaseUserEntity getCurrentUser() {
        return getCurrentUserOptional()
                .orElseThrow(()-> new IllegalStateException("Could not retrieve current user!"));    }

    @Override
    public Long getCurrentUserFileId(UserEntity userId) {
        return userFileRepository.findByUserId(userId).getId();
    }

    @Override
    public Boolean userHasRole(BaseUserEntity user, Role role) {
        return userRepo.getUserRoles(user)
                .stream()
                .anyMatch(auth -> Objects.equals( auth, role.getValue()));
    }

    @Override
    public Boolean userHasRole(Role role)
    {
        return getCurrentUserRolesNames()
                .stream()
                .anyMatch(auth -> Objects.equals( auth, role.getValue()));
    }

    @Override
    public Boolean currentUserHasRole(Role role) {
        return getCurrentUserRolesNames()
                .stream()
                .anyMatch(auth -> Objects.equals( auth, role.getValue()));
    }

    private List<String> getCurrentUserRolesNames() {
        return ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .orElse(emptyList())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Objects::nonNull)
                .collect(toList());
    }


    @Override
    public Optional<BaseUserEntity> getCurrentUserOptional() {
        return ofNullable( SecurityContextHolder.getContext() )
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getDetails)
                .map(BaseUserEntity.class::cast);
    }

    @Override
    public boolean currentUserHasMaxRoleLevelOf(Role role) {
        return false;
    }

    @Override
    public Set<Role> getCurrentUserRoles() {
        return getCurrentUserRolesNames()
                .stream()
                .map(Role::fromString)
                .collect(toSet());    }

    private UserAuthenticationData createUserAuthData(UserToken userTokens) {
        BaseUserEntity userEntity =
                userTokens
                        .getBaseUser()
                        .orElseThrow(() -> new RuntimeException(INTERNAL_SERVER_ERROR+ " " + "NO USER FOUND FOR A TOKEN!"));

        List<GrantedAuthority> roles = getUserRoles(userEntity);
        User user= new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,roles);
        return new UserAuthenticationData(user, userEntity, userTokens);
    }

    private Optional<UserToken> getUserByAuthenticationToken(String token) {
        UserToken userTokens = userTokenRepository.getUserEntityByToken(token);
        if (userTokens == null || isExpired(userTokens)) {
            return empty();
        }
        return ofNullable(userTokens);
    }

    private List<GrantedAuthority> getUserRoles(BaseUserEntity userEntity) {
        return userRepo.getUserRoles(userEntity).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }
    private String generateUserToken(BaseUserEntity user) {
        UserToken token = new UserToken();
        token.setToken(StringUtils.generateUUIDToken());
        if (user instanceof UserEntity  && ((UserEntity) user).getRoles().equals(Role.ADMIN)) {
            token.setUserEntity((UserEntity) user);
        }
        userTokenRepository.save(token);

        return token.getToken();
    }

    private boolean isExpired(UserToken userTokens) {
        return ofNullable(userTokens)
                .map(UserToken::getUpdateTime)
                .map(updateTime -> Duration.between(updateTime, now()))
                .filter(liveTime -> liveTime.getSeconds() >= AUTH_TOKEN_VALIDITY)
                .isPresent();
    }



    @Data
    @AllArgsConstructor
    class UserPostLoginData{
        private BaseUserEntity userEntity;
        private String token;
    }
}
