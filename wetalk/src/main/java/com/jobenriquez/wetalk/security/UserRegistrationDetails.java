package com.jobenriquez.wetalk.security;

/**
 * @author Isaiah Job Cuenca Enriquez
 */

import com.jobenriquez.wetalk.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserRegistrationDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> authorities;

    public UserRegistrationDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
