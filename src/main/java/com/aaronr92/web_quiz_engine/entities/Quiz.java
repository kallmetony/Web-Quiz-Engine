package com.aaronr92.web_quiz_engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity(name = "quiz")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "quiz_id")
    private long id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String text;

    @ElementCollection
    @NotNull
    @Size(min = 2)
    private List<String> options;

    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> answer;

    @JsonIgnore
    @Column(name = "created_by")
    private String email;
}