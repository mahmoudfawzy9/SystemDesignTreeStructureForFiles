package com.mahmoud.stc.repository;

import com.mahmoud.stc.entity.BaseUserEntity;
import java.util.List;

public interface CommonUserRepository{
  
    List<String> getUserRoles(BaseUserEntity user);
  
}
