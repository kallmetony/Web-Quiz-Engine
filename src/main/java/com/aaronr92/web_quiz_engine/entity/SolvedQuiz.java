package com.aaronr92.web_quiz_engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class SolvedQuiz {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long solved_id;

    @Column
    private long id;

    @Column
    @CreationTimestamp
    private LocalDateTime completedAt;

    @JsonIgnore
    @Column
    private String username;

    public SolvedQuiz(long id, String username) {
        this.id = id;
        this.username = username;
    }
}
