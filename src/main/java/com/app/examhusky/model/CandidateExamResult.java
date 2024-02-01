package com.app.examhusky.model;

import com.app.examhusky.model.enums.Recommendation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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
    private Account decidedBy;

    public CandidateExamResult() {
    }

    public CandidateExamResult(Integer id, Candidate candidate, Exam exam, Integer totalMarks, Boolean examined,
                               Recommendation recommendation, Boolean published, Account decidedBy) {
        this.id = id;
        this.candidate = candidate;
        this.exam = exam;
        this.totalMarks = totalMarks;
        this.examined = examined;
        this.recommendation = recommendation;
        this.published = published;
        this.decidedBy = decidedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Boolean getExamined() {
        return examined;
    }

    public void setExamined(Boolean examined) {
        this.examined = examined;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Account getDecidedBy() {
        return decidedBy;
    }

    public void setDecidedBy(Account decidedBy) {
        this.decidedBy = decidedBy;
    }
}