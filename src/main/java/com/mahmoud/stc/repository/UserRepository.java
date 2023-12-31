package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

     Optional<UserEntity> findById(Long userId);

     Optional<UserEntity> findByUserName(String username);
}
