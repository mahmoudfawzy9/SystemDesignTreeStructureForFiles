package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.BaseUserEntity;

import java.util.List;
import java.util.Optional;

public interface CommonUserRepository{
  
    List<String> getUserRoles(BaseUserEntity user);


    BaseUserEntity saveAndFlush(BaseUserEntity userEntity);

    Optional<BaseUserEntity> findById(Long id, Boolean isAdmin);

    Optional<BaseUserEntity> findByUserName(String username);
}
