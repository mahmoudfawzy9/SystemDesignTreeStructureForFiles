package com.mahmoud.stc.repository.Impl;

import com.mahmoud.stc.entity.BaseUserEntity;
import com.mahmoud.stc.entity.UserEntity;
import com.mahmoud.stc.repository.CommonUserRepository;
import com.mahmoud.stc.repository.UserRepository;
import com.mahmoud.stc.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@Repository
public class CommonUserRepositoryImpl implements CommonUserRepository {

    @Autowired
    private UserRepository userRepo;


    @Override
    public List<String> getUserRoles(BaseUserEntity user) {

        if (user == null)
            return new ArrayList<>();


        if (user instanceof UserEntity)
            return getCustomerUserRoles();

        return null;
    }


    private List<String> getCustomerUserRoles() {
        // for now, return default role which is Customer
        return Collections.singletonList(Role.VIEW.name());
    }


    @Override
    public BaseUserEntity saveAndFlush(BaseUserEntity userEntity) {

         if (userEntity instanceof UserEntity)
            return userRepo.saveAndFlush((UserEntity) userEntity);
        else
            throw new RuntimeException(String.valueOf(INTERNAL_SERVER_ERROR));
    }


    @Override
    public Optional<BaseUserEntity> findById(Long id, Boolean isAdmin) {
        return userRepo.findById(id)
                .map(BaseUserEntity.class::cast);
    }

    @Override
    public  Optional<BaseUserEntity> findByUserName(String username) {
        return userRepo.findByUserName(username)
                .map(BaseUserEntity.class::cast);
    }
}
