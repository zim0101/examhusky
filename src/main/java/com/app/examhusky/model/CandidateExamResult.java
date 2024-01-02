package com.app.examhusky.model;

import com.app.examhusky.model.enums.Recommendation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CandidateExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
            "candidate_exam_result_generator")
    @SequenceGenerator(name = "candidate_exam_result_generator", sequenceName =
            "candidate_exam_result_seq")
    private Integer id;

    @ManyToOne
    @NotNull
    private Candidate candidate;

    @ManyToOne
    @NotNull
    private Exam exam;

    @NotNull
    private Integer totalMarks = 0;

    @NotNull
    private Boolean examined = false;

    @NotNull
    private Recommendation recommendation = Recommendation.NOT_DECIDED;

    @NotNull
    private Boolean published = false;

    @ManyToOne
    @NotNull
    private Account decidedBy;
}