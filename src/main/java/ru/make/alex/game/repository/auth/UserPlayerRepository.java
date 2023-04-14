package ru.make.alex.game.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.make.alex.game.model.auth.UserPlayer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPlayerRepository extends JpaRepository<UserPlayer, UUID> {
    Optional<UserPlayer> findByEmail(String email);
    Optional<UserPlayer> findByLogin(String login);
}
