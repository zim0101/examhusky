package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Date beginDate;

    private Boolean published;

    @ManyToMany(mappedBy = "exams")
    private List<Candidate> candidates;

    @ManyToMany(mappedBy = "exams")
    private List<Examiner> examiners;

    @ManyToMany(mappedBy = "exams")
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CandidateExamAnswerRecord> candidateExamAnswerRecord;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}