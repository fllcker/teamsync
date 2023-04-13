package ru.fllcker.teamsync.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "channels")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Channel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
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
