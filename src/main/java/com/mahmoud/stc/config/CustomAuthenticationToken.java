package com.mahmoud.stc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomAuthenticationToken implements Authentication {
    private  String username;

    private boolean authenticated;

    private  AccessToken accessToken;

    public CustomAuthenticationToken(String username, boolean authenticated) {
        this.username = username;
        this.authenticated = authenticated;
    }

    public CustomAuthenticationToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean  isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }
}
