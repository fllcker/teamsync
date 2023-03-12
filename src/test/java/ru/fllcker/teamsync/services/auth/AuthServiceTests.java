package ru.fllcker.teamsync.services.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.fllcker.teamsync.dto.SignUpDto;
import ru.fllcker.teamsync.models.Token;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.ITokensRepository;
import ru.fllcker.teamsync.security.JwtRequest;
import ru.fllcker.teamsync.security.JwtResponse;
import ru.fllcker.teamsync.services.users.UsersService;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @Mock
    private UsersService usersService;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private ITokensRepository tokensRepository;


    @InjectMocks
    private AuthService authService;

    private static User user;

    @BeforeAll
    public static void BeforeAll() {
        user = User.builder()
                .email("TestUser@gmail.com")
                .password("TestPassword123")
                .firstName("TestUserName")
                .lastName("TestUserLast")
                .build();
    }

    @Test
    public void AuthService_SignUp_ReturnsJwtResponse() {
        Token token = new Token();
        token.setToken("TOKEN.TOKEN.TOKEN");
        SignUpDto dto = new SignUpDto(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());

        when(usersService.create(Mockito.any(User.class))).thenReturn(user);
        when(encoder.encode(dto.getPassword())).thenReturn("HASHED_PASSWORD");
        when(jwtProvider.generateToken(Mockito.any(User.class), Mockito.anyBoolean())).thenReturn("TOKEN.TOKEN.TOKEN");
        when(tokensRepository.save(Mockito.any(Token.class))).thenReturn(token);

        JwtResponse result = authService.signup(dto);

        Assertions.assertNotNull(result.getAccessToken());
        Assertions.assertNotNull(result.getRefreshToken());
        Assertions.assertEquals("Bearer", result.getType());
    }

    @Test
    public void AuthService_Login_ReturnsJwtResponse() {
        Token token = new Token();
        token.setToken("TOKEN.TOKEN.TOKEN");
        JwtRequest jwtRequest = new JwtRequest(user.getEmail(), user.getPassword());

        when(usersService.findByEmail(user.getEmail())).thenReturn(user);
        when(encoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(jwtProvider.generateToken(Mockito.any(User.class), Mockito.anyBoolean())).thenReturn("TOKEN.TOKEN.TOKEN");
        when(tokensRepository.save(Mockito.any(Token.class))).thenReturn(token);

        JwtResponse result = authService.login(jwtRequest);

        Assertions.assertNotNull(result.getAccessToken());
        Assertions.assertNotNull(result.getRefreshToken());
        Assertions.assertEquals("Bearer", result.getType());
    }
}
