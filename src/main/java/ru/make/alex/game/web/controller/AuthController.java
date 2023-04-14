package ru.make.alex.game.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.make.alex.game.config.security.jwt.TokenService;
import ru.make.alex.game.service.auth.CustomUserDetailsService;
import ru.make.alex.game.service.auth.UserProfileService;
import ru.make.alex.game.web.dto.auth.CreateUserRequestDto;
import ru.make.alex.game.web.dto.auth.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserProfileService userProfileService;


    record LoginRequest(String username, String password) {
    }

    record LoginResponse(String message, String access_jwt_token, String refresh_jwt_token) {
    }

    record RefreshTokenResponse(String access_jwt_token, String refresh_jwt_token) {
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.username, request.password);
        authManager.authenticate(authenticationToken);

        var user = (CustomUserDetails) userDetailsService.loadUserByUsername(request.username);
        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new LoginResponse("User with login = " + request.username + " successfully logined!",
                access_token,
                refresh_token);
    }

    @GetMapping("/token/refresh")
    public RefreshTokenResponse refreshToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String refreshToken = headerAuth.substring(7, headerAuth.length());

        String login = tokenService.parseToken(refreshToken);
        var user = (CustomUserDetails) userDetailsService.loadUserByUsername(login);
        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new RefreshTokenResponse(access_token, refresh_token);
    }

    record AddUserRequest(String username, String password, String email) {
    }

    record AddUserResponse(String username, Boolean result) {
    }

    @PostMapping("/add/player")
    public AddUserResponse addUser(@RequestBody AddUserRequest request) {
        userProfileService.createPlayer(CreateUserRequestDto.builder()
                .login(request.username)
                .password(request.password)
                .email(request.email)
                .build());
        return new AddUserResponse(request.username, Boolean.TRUE);
    }
}
