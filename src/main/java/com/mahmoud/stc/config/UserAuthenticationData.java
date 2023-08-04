package com.mahmoud.stc.config;

import com.mahmoud.stc.entity.BaseUserEntity;
import com.mahmoud.stc.entity.UserToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
public class UserAuthenticationData {
  
    private UserDetails userDetails;
  
    private BaseUserEntity userEntity;
  
    private UserToken token;
}
