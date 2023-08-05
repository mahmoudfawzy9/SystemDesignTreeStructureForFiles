package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.UserEntity;
import com.mahmoud.stc.entity.UserToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserTokenRepository extends CrudRepository<UserToken, Long> {


    @Query("SELECT t FROM UserToken t left join fetch t.userEntity" +
            " where t.token = :token")
    UserToken getUserEntityByToken(@Param("token") String token);


    @Transactional
    @Modifying
    @Query(value = "delete from UserToken t where t.userEntity = :usr")
    void deleteByUserEntity(@Param("usr") UserEntity usr);


    UserToken saveAndFlush(UserToken userToken);
}

