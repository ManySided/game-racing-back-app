package ru.make.alex.game.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController()
@RequestMapping("/admin/api")
public class AdminController {
    @GetMapping("/now")
    public String currentAdmin() {
        return OffsetDateTime.now().toString();
    }
}
