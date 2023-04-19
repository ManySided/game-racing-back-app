package ru.make.alex.game.service.room;

import ru.make.alex.game.web.dto.room.PlayerRoomDto;
import ru.make.alex.game.web.dto.room.RoomDto;

import java.util.UUID;


public interface RoomService {
    String createRoom(RoomDto dto);

    PlayerRoomDto joinRoom(UUID roomId);

    void leaveRoom();
}
