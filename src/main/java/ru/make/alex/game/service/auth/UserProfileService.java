package ru.make.alex.game.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.make.alex.game.model.auth.UserPlayer;
import ru.make.alex.game.repository.auth.UserPlayerRepository;
import ru.make.alex.game.repository.auth.UserRoleRepository;
import ru.make.alex.game.utils.security.SecurityHelper;
import ru.make.alex.game.web.dto.auth.CreateUserRequestDto;
import ru.make.alex.game.web.dto.auth.ProfileDto;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {
    private final UserPlayerRepository userPlayerRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ProfileDto createPlayer(CreateUserRequestDto request) {
        var usrRole = userRoleRepository.findByName("usr");

        var pwd = encodePassword(request.getPassword());
        userPlayerRepository.save(UserPlayer.builder()
                .login(request.getLogin())
                .password(pwd)
                .email(request.getEmail())
                .roles(Set.of(usrRole))
                .build());
        return ProfileDto.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .now(OffsetDateTime.now().toString())
                .build();
    }

    public ProfileDto getCurrentUser(String login) {
        var user = userPlayerRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + login + "' not found"));
        return ProfileDto.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .now(OffsetDateTime.now().toString())
                .build();
    }

    public UUID getUserId() {
        return userPlayerRepository.getUserId(SecurityHelper.getCurrentLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User \"" + SecurityHelper.getCurrentLogin() + "\" not found"));
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
