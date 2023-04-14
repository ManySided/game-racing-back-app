package ru.make.alex.game.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.make.alex.game.web.dto.auth.ProfileDto;

import java.time.OffsetDateTime;

@RestController()
@RequestMapping("/user/api/profile")
public class ProfileController {
    @GetMapping("/current")
    public ProfileDto currentProfile() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ProfileDto.builder()
                .login(auth.getName())
                //.email() // нужно прям запрос сделать, чтобы получить все данные
                .now(OffsetDateTime.now().toString())
                .build();
    }
}
