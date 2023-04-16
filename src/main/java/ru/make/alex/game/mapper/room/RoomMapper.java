package ru.make.alex.game.mapper.room;

import org.mapstruct.Mapper;
import ru.make.alex.game.model.room.Room;
import ru.make.alex.game.web.dto.room.RoomDto;

@Mapper
public interface RoomMapper {
    Room toEntity(RoomDto dto);

    RoomDto toDto(Room room);
}
