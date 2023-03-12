package ru.fllcker.teamsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.messages.NewMessageDto;
import ru.fllcker.teamsync.models.Message;
import ru.fllcker.teamsync.services.auth.AuthService;
import ru.fllcker.teamsync.services.messages.MessagesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/messages")
public class MessagesController {
    private final AuthService authService;
    private final MessagesService messagesService;

    @PostMapping
    public ResponseEntity<Message> create(@RequestBody @Valid NewMessageDto newMessageDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        String accessEmail = authService.getAuthInfo().getEmail();

        Message message = messagesService.create(accessEmail, newMessageDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping("channel/{channelId}")
    public ResponseEntity<List<Message>> findByChannel(@PathVariable Long channelId) {
        String accessEmail = authService.getAuthInfo().getEmail();

        List<Message> messages = messagesService.findByChannel(accessEmail, channelId);
        return ResponseEntity.ok(messages);
    }
}
