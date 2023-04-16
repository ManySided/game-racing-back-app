package ru.make.alex.game.utils.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@UtilityClass
public class SecurityHelper {
    public String getCurrentLogin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(auth))
            return auth.getName();
        return "<empty user>";
    }
}
