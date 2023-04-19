package ru.fllcker.teamsync.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "spaces")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Space {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    @Column(name = "title")
    private String title;

    // Members of space
    @ManyToMany
    @JoinTable(
            name = "spaces_users",
            joinColumns = @JoinColumn(name = "space_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    // Owner of space
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    private User owner;

    // Channels in space
    @OneToMany(mappedBy = "space")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Channel> channels;

    // Categories for channels
    @OneToMany(mappedBy = "space")
    @JsonBackReference
    private List<Category> categories;

    // Invite codes for invite to space
    @OneToMany(mappedBy = "space")
    @JsonBackReference
    private List<InviteCode> inviteCodes;
}
