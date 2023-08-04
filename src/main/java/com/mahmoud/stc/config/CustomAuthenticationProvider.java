package com.mahmoud.stc.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import  com.mahmoud.stc.exception.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public boolean supports(Class<?> authenticationToken) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = "pass123"; // Hardcoded password

        if (username.equals("admin") && password.equals(authentication.getCredentials())) {
            return new CustomAuthenticationToken(username, true);
        } else {
            throw new AuthenticationException("Invalid credentials");
        }

    }
}
