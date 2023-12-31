package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
public class CandidateExamAnswerRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
            "candidate_exam_answer_record_generator")
    @SequenceGenerator(name = "candidate_exam_answer_record_generator", sequenceName =
            "candidate_exam_answer_record_seq")
    private Integer id;

    @Transient
    private String encryptedId;

    @ManyToOne
    @NotNull
    private Candidate candidate;

    @ManyToOne
    @NotNull
    private Exam exam;

    @ManyToOne
    @NotNull
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @NotNull
    private Integer marks = 0;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}