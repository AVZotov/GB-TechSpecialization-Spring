package ru.gb.lesson05.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private long id;
    @Column(nullable = false)
    @NonNull
    private String description;
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.NOT_STARTED;
    @Column
    private final LocalDateTime dateTime = LocalDateTime.now();
}
