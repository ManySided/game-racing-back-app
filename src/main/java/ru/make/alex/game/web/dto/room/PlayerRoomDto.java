package ru.make.alex.game.web.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerRoomDto {
    private UUID id;
    private UUID room;
    private UUID userPlayer;
    private OffsetDateTime joinDate;
}
