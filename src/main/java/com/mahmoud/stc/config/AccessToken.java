package com.mahmoud.stc.config;

import java.util.List;

public class AccessToken {

    private final String clientId;
    private final String clientSecret;
    private final List<String> scope;

    public AccessToken(String clientId, String clientSecret, List<String> scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public List<String> getScope() {
        return scope;
    }
}
