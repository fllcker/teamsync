package ru.fllcker.teamsync.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channels")
@NoArgsConstructor
public class Channel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "id")
    @JsonManagedReference
    private Space space;

    @OneToMany(mappedBy = "channel")
    @JsonBackReference
    private List<Message> messages;
}
