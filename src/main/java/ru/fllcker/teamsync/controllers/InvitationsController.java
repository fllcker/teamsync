package ru.fllcker.teamsync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.invitations.NewInviteCode;
import ru.fllcker.teamsync.models.InviteCode;
import ru.fllcker.teamsync.services.invitations.InvitationsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/invitations")
public class InvitationsController {
    private final InvitationsService invitationsService;

    @PostMapping
    public ResponseEntity<InviteCode> create(@RequestBody @Valid NewInviteCode newInviteCode, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());

        InviteCode inviteCode = invitationsService.create(newInviteCode);
        return ResponseEntity.ok(inviteCode);
    }
}
