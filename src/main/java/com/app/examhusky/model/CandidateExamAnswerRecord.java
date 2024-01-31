package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.util.Date;

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

    public CandidateExamAnswerRecord() {
    }

    public CandidateExamAnswerRecord(Integer id, String encryptedId, Candidate candidate, Exam exam,
                                     Question question, String answer, Integer marks, Date createdAt, Date updatedAt) {
        this.id = id;
        this.encryptedId = encryptedId;
        this.candidate = candidate;
        this.exam = exam;
        this.question = question;
        this.answer = answer;
        this.marks = marks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}