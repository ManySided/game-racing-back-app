package ru.make.alex.game.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.make.alex.game.repository.auth.UserPlayerRepository;
import ru.make.alex.game.web.dto.auth.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserPlayerRepository userPlayerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userPlayer = userPlayerRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь [" + username + "] не найден"));
        return new CustomUserDetails(userPlayer);
    }
}
