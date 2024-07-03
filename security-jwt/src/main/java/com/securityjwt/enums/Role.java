package com.securityjwt.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    USER("USER"),
    CUSTOMER ("CUSTOMER"),
    PERFORMER ("PERFORMER");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}

