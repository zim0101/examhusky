package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
public class CandidateExamAnswerMark {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
            "candidate_exam_answer_mark_generator")
    @SequenceGenerator(name = "candidate_exam_answer_mark_generator", sequenceName =
            "candidate_exam_answer_mark_seq")
    private Integer id;

    @OneToOne
    @NotNull
    private CandidateExamAnswerRecord candidateExamAnswerRecord;

    @ManyToOne
    @NotNull
    private Examiner examiner;

    @NotNull
    private Integer marks;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}