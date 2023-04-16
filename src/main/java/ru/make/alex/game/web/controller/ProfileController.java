package ru.make.alex.game.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.make.alex.game.service.auth.UserProfileService;
import ru.make.alex.game.web.dto.auth.ProfileDto;

@RestController()
@RequestMapping("/user/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserProfileService service;

    @GetMapping("/current")
    public ProfileDto currentProfile() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return service.getCurrentUser(auth.getName());
    }
}
