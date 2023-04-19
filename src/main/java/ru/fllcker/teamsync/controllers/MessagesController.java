package ru.fllcker.teamsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.messages.NewMessageDto;
import ru.fllcker.teamsync.models.Message;
import ru.fllcker.teamsync.services.messages.MessagesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/messages")
public class MessagesController {
    private final MessagesService messagesService;

    @PostMapping
    public ResponseEntity<Message> create(@RequestBody @Valid NewMessageDto newMessageDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        Message message = messagesService.create(newMessageDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping("channel/{channelId}")
    public Page<Message> findByChannel(@PathVariable Long channelId,
                                                       @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                       @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        return messagesService.findByChannel(channelId, offset, limit);
    }
}
