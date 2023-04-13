package ru.fllcker.teamsync.services.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.SignUpDto;
import ru.fllcker.teamsync.models.Token;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.ITokensRepository;
import ru.fllcker.teamsync.security.JwtAuthentication;
import ru.fllcker.teamsync.security.JwtRequest;
import ru.fllcker.teamsync.security.JwtResponse;
import ru.fllcker.teamsync.services.users.UsersService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder encoder;
    private final ITokensRepository tokensRepository;
    private final UsersService usersService;
    private final JwtProvider jwtProvider;

    public JwtResponse login(JwtRequest jwtRequest) {
        User user = usersService.findByEmail(jwtRequest.getEmail());

        if (!encoder.matches(jwtRequest.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong data");

        String accessToken = jwtProvider.generateToken(user, false);
        String refreshToken = jwtProvider.generateToken(user, true);
        tokensRepository.save(new Token(user, refreshToken));

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse signup(SignUpDto dto) {
        if (usersService.existsByEmail(dto.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with this email already exists!");

        User user = User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

        usersService.create(user);

        String accessToken = jwtProvider.generateToken(user, false);
        String refreshToken = jwtProvider.generateToken(user, true);
        tokensRepository.save(new Token(user, refreshToken));

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse getTokensByRefresh(String refreshToken, boolean refresh) {
        String subject = jwtProvider.getRefreshClaims(refreshToken)
                .getSubject();

        if (!jwtProvider.validateRefreshToken(refreshToken))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Token token = tokensRepository.findByValue(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));

        if (!token.getValue().equals(refreshToken) || !token.getOwner().getEmail().equals(subject))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);


        User user = usersService.findByEmail(subject);

        String accessToken = jwtProvider.generateToken(user, false);
        String newRefreshToken = null;

        if (refresh) {
            newRefreshToken = jwtProvider.generateToken(user, true);
            tokensRepository.save(new Token(user, newRefreshToken));
        }

        return new JwtResponse(accessToken, newRefreshToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
