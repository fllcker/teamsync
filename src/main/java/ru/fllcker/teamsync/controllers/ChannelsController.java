package ru.fllcker.teamsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.channels.NewChannelDto;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.services.channels.ChannelsService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/channels")
public class ChannelsController {
    private final ChannelsService channelsService;

    @PostMapping
    public ResponseEntity<Channel> create(@RequestBody @Valid NewChannelDto newChannelDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        Channel channel = channelsService.create(newChannelDto);
        return ResponseEntity.ok(channel);
    }

    @GetMapping("space/{spaceId}")
    public ResponseEntity<List<Channel>> findBySpace(@PathVariable Long spaceId) {
        List<Channel> channels = channelsService.findBySpace(spaceId);
        return ResponseEntity.ok(channels);
    }

    @GetMapping("space/{spaceId}/group/category")
    public ResponseEntity<Map<Long, List<Channel>>> findBySpaceAndGrouped(@PathVariable Long spaceId) {
        Map<Long, List<Channel>> channels = channelsService.findBySpaceAndGrouped(spaceId);
        return ResponseEntity.ok(channels);
    }

    @PatchMapping("category/{channelId}/{categoryId}")
    public ResponseEntity<Channel> updateCategory(@PathVariable Long channelId, @PathVariable Long categoryId) {
        Channel result = channelsService.updateCategory(channelId, categoryId);
        return ResponseEntity.ok(result);
    }
}
