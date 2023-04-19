package ru.make.alex.game.service.room.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.make.alex.game.expetion.GameDuringException;
import ru.make.alex.game.expetion.NotFoundException;
import ru.make.alex.game.mapper.room.PlayerRoomMapper;
import ru.make.alex.game.mapper.room.RoomMapper;
import ru.make.alex.game.model.room.PlayerRoom;
import ru.make.alex.game.repository.room.PlayerRoomRepository;
import ru.make.alex.game.repository.room.RoomRepository;
import ru.make.alex.game.service.auth.UserProfileService;
import ru.make.alex.game.service.room.RoomService;
import ru.make.alex.game.utils.security.SecurityHelper;
import ru.make.alex.game.web.dto.room.PlayerRoomDto;
import ru.make.alex.game.web.dto.room.RoomDto;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final PlayerRoomRepository playerRoomRepository;
    private final UserProfileService userProfileService;
    private final RoomMapper roomMapper;
    private final PlayerRoomMapper playerRoomMapper;

    @Override
    @Transactional
    public String createRoom(RoomDto dto) {
        log.info("create room by user [{}]", SecurityHelper.getCurrentLogin());
        var roomCreateRequest = roomMapper.toEntity(dto);
        roomCreateRequest.setCreateDate(OffsetDateTime.now());

        var room = roomRepository.save(roomCreateRequest);
        log.info("the room [{}] was created", room.getId());

        playerRoomRepository.save(PlayerRoom.builder()
                .joinDate(OffsetDateTime.now())
                .userPlayer(userProfileService.getUserId())
                .room(room.getId())
                .build());
        log.info("user [{}] has been added to the room [{}]", SecurityHelper.getCurrentLogin(), room.getId());

        return room.getId().toString();
    }

    @Override
    @Transactional
    public PlayerRoomDto joinRoom(UUID roomId) {
        var userLogin = SecurityHelper.getCurrentLogin();
        var userId = userProfileService.getUserId();
        log.info("user [{}] wants to join the room [{}]", userLogin, roomId);
        var playerResponse = playerRoomRepository.findByUserPlayerAndLeaveDateIsNull(userId);
        if (!playerResponse.isEmpty()) {
            log.info("user [{}] is already in the room [{}]", userId, playerResponse.get(0).getRoom());
            throw new GameDuringException("The user is already in the room");
        }

        roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException("room [" + roomId + "] not found"));

        var players = playerRoomRepository.findByRoomAndLeaveDateIsNull(roomId);
        if (players.size() >= 4) {
            log.info("user [{}] cant to join the room [{}]. Players count [{}]",
                    userLogin,
                    roomId,
                    players.size());
            throw new GameDuringException("The room is full. Only four players");
        }

        var player = playerRoomRepository.save(PlayerRoom.builder()
                .joinDate(OffsetDateTime.now())
                .userPlayer(userId)
                .room(roomId)
                .build());
        log.info("user [{}] added to room [{}]. Player [{}]", userLogin, roomId, player.getId());

        return playerRoomMapper.toDto(player);
    }

    @Override
    @Transactional
    public void leaveRoom() {
        var userLogin = SecurityHelper.getCurrentLogin();
        var userId = userProfileService.getUserId();

        var playerResponse = playerRoomRepository.findByUserPlayerAndLeaveDateIsNull(userId);
        if (playerResponse.isEmpty()) {
            log.info("user [{}] is not in any room", userLogin);
            throw new GameDuringException("The user is not in any room");
        } else {
            var player = playerResponse.get(0);
            log.info("user [{}] wants to leave the room [{}]", userLogin, player.getRoom());
            player.setLeaveDate(OffsetDateTime.now());
            playerRoomRepository.save(player);
            log.info("user [{}]  has left the room [{}]", userLogin, player.getRoom());
        }
    }
}
