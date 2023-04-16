package ru.make.alex.game.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.make.alex.game.service.room.RoomService;
import ru.make.alex.game.web.dto.room.RoomDto;

@RestController()
@RequestMapping("/user/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public String createRoom(@RequestBody RoomDto request) {
        return roomService.createRoom(request);
    }
}
