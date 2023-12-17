package com.app.examhusky.model;

import com.app.examhusky.model.enums.QuestionDifficulty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq")
    private Integer id;

    @NotNull
    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String title;

    private QuestionDifficulty difficultyLevel;

    @ManyToOne
    @NotNull
    private QuestionCategory category;

    @ManyToOne
    private Account editor;

    @ManyToMany
    @JoinTable(
            name = "exam_question",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private List<Exam> exams;

    private Boolean deleted = false;

    private Boolean disabled = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}