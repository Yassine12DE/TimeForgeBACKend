package tn.esprit.tic.timeforge.Service.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.tic.timeforge.Entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Vous pouvez personnaliser ce comportement
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Vous pouvez personnaliser ce comportement
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Vous pouvez personnaliser ce comportement
    }

    @Override
    public boolean isEnabled() {
        return true; // Vous pouvez personnaliser ce comportement
    }

    public User getUser() {
        return user;
    }
}

