package ru.make.alex.game.model.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRoom {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "room_player_id")
    private UUID id;

    private UUID room;

    private UUID userPlayer;

    private OffsetDateTime joinDate;
    private OffsetDateTime leaveDate;
}
