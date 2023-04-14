package ru.make.alex.game.web.dto.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.make.alex.game.model.auth.UserPlayer;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final UserPlayer userPlayer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userPlayer.getRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userPlayer.getPassword();
    }

    @Override
    public String getUsername() {
        return userPlayer.getLogin();
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
        return true;
    }

    public UserPlayer getUserPlayer() {
        return userPlayer;
    }
}
