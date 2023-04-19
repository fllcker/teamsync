package ru.fllcker.teamsync.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Category {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Channel> channels;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "id")
    private Space space;
}
