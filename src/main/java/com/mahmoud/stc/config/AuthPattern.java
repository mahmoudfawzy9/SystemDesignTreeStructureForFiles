package com.mahmoud.stc.config;

import com.mahmoud.stc.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthPattern {
  
    private String urlPattern;
  
    private HttpMethod httpMethod;
  
    private Set<Role> roles;
}
