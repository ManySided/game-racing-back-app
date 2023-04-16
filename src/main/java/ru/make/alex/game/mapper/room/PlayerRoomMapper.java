package ru.make.alex.game.mapper.room;

import org.mapstruct.Mapper;
import ru.make.alex.game.model.room.PlayerRoom;
import ru.make.alex.game.web.dto.room.PlayerRoomDto;

@Mapper
public interface PlayerRoomMapper {
    PlayerRoom toEntity(PlayerRoomDto dto);

    PlayerRoomDto toDto(PlayerRoom room);
}
