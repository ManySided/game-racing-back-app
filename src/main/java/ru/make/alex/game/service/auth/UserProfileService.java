package ru.make.alex.game.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.make.alex.game.model.auth.UserPlayer;
import ru.make.alex.game.repository.auth.UserPlayerRepository;
import ru.make.alex.game.repository.auth.UserRoleRepository;
import ru.make.alex.game.web.dto.auth.CreateUserRequestDto;
import ru.make.alex.game.web.dto.auth.ProfileDto;

import java.util.Set;

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
                .build();
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
