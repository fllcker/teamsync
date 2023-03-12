package ru.fllcker.teamsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.channels.NewChannelDto;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.services.auth.AuthService;
import ru.fllcker.teamsync.services.channels.ChannelsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/channels")
public class ChannelsController {
    private final AuthService authService;
    private final ChannelsService channelsService;

    @PostMapping
    public ResponseEntity<Channel> create(@RequestBody @Valid NewChannelDto newChannelDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        String accessEmail = authService.getAuthInfo().getEmail();

        Channel channel = channelsService.create(accessEmail, newChannelDto);
        return ResponseEntity.ok(channel);
    }

    @GetMapping("space/{spaceId}")
    public ResponseEntity<List<Channel>> findBySpace(@PathVariable Long spaceId) {
        String accessEmail = authService.getAuthInfo().getEmail();

        List<Channel> channels = channelsService.findBySpace(accessEmail, spaceId);
        return ResponseEntity.ok(channels);
    }
}
