package ru.fllcker.teamsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.spaces.NewSpaceDto;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.services.spaces.SpacesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/spaces")
public class SpacesController {
    private final SpacesService spacesService;

    @PostMapping
    public ResponseEntity<Space> create(@RequestBody @Valid NewSpaceDto newSpaceDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        Space space = spacesService.create(newSpaceDto);
        return ResponseEntity.ok(space);
    }

    @GetMapping("members/{spaceId}")
    public ResponseEntity<List<User>> findMembers(@PathVariable Long spaceId) {
        List<User> members = spacesService.findMembers(spaceId);
        return ResponseEntity.ok(members);
    }

    @GetMapping("my")
    public ResponseEntity<List<Space>> findUserSpaces() {
        List<Space> spaces = spacesService.findByMember();
        return ResponseEntity.ok(spaces);
    }
}
