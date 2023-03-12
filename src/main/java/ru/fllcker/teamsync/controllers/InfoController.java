package ru.fllcker.teamsync.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/info")
public class InfoController {
    @GetMapping("ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
}
