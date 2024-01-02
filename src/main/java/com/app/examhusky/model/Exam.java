package com.app.examhusky.model;

import com.app.examhusky.model.enums.ExamState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Exam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exam_generator")
    @SequenceGenerator(name = "exam_generator", sequenceName = "exam_seq")
    private Integer id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private Date startDate;

    private ExamState state = ExamState.PENDING;

    @NotNull
    @Min(value = 1, message = "Duration must be at least 1 min!")
    private Integer duration;

    @ManyToMany(mappedBy = "exams", fetch = FetchType.LAZY)
    private List<Candidate> candidates;

    @ManyToMany(mappedBy = "exams", fetch = FetchType.LAZY)
    private List<Examiner> examiners;

    @ManyToMany(mappedBy = "exams", fetch = FetchType.LAZY)
    private List<Question> questions;

    private Boolean deleted = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}