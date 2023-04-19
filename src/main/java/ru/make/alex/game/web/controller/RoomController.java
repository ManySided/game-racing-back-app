package ru.make.alex.game.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.make.alex.game.service.room.RoomService;
import ru.make.alex.game.web.dto.room.PlayerRoomDto;
import ru.make.alex.game.web.dto.room.RoomDto;

import java.util.UUID;

@RestController()
@RequestMapping("/user/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public String createRoom(@RequestBody RoomDto request) {
        return roomService.createRoom(request);
    }

    @PostMapping("/addUser")
    public PlayerRoomDto createRoom(@RequestBody UUID roomId) {
        return roomService.joinRoom(roomId);
    }

    @PostMapping("/kickUser")
    public ResponseEntity<?> createRoom() {
        roomService.leaveRoom();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }
}
