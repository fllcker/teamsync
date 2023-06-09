package ru.fllcker.teamsync.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    @Column(name = "email")
    private String email;

    @NonNull
    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;


    // rels
    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Token> tokens;


    @ManyToMany(mappedBy = "members")
    @JsonBackReference
    private List<Space> spaces;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Space> ownedSpaces;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Message> messages;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<InviteCode> inviteCodes;

}
