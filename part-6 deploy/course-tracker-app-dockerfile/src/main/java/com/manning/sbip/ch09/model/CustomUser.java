package com.manning.sbip.ch09.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private static final long serialVersionUID = -3838670851167286190L;
    private boolean totpEnabled;

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public boolean isTotpEnabled() {
        return totpEnabled;
    }

    public void setTotpEnabled(boolean totpEnabled) {
        this.totpEnabled = totpEnabled;
    }
}
