package ru.fllcker.teamsync.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    @NonNull
    @Column(name = "title")
    private String title;

    @ManyToMany
    @JoinTable(
            name = "spaces_users",
            joinColumns = @JoinColumn(name = "space_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> members;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    private User owner;

    @OneToMany(mappedBy = "space")
    @JsonBackReference
    private List<Channel> channels;

    @OneToMany(mappedBy = "space")
    @JsonBackReference
    private List<Category> categories;
}
