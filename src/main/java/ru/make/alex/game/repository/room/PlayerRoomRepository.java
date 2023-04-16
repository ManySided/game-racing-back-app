package ru.make.alex.game.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.make.alex.game.model.room.PlayerRoom;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRoomRepository extends JpaRepository<PlayerRoom, UUID> {
    List<PlayerRoom> findByRoom(UUID room);

    List<PlayerRoom> findByRoomAndLeaveDateIsNull(UUID room);

    List<PlayerRoom> findByUserPlayerAndLeaveDateIsNull(UUID userPlayer);
}
