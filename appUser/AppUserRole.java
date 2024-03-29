package com.example.kursovaya_rabota.appUser;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
    ROLE_USER, ROLE_WAITER, ROLE_COOK, ROLE_ANALYST, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
