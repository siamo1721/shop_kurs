package com.example.kurs.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
@Getter
public enum UserAuthority implements GrantedAuthority {
    admin,
    manager,
    seller,
    customer;

    @Override
    public String getAuthority() {
        return this.name();
    }
}