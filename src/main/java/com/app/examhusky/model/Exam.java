package com.app.examhusky.model;

import com.app.examhusky.model.enums.ExamState;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDate startDate;

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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Exam() {
    }

    public Exam(Integer id, String title, LocalDate startDate, ExamState state, Integer duration,
                List<Candidate> candidates, List<Examiner> examiners, List<Question> questions, Boolean deleted,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.state = state;
        this.duration = duration;
        this.candidates = candidates;
        this.examiners = examiners;
        this.questions = questions;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ExamState getState() {
        return state;
    }

    public void setState(ExamState state) {
        this.state = state;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public List<Examiner> getExaminers() {
        return examiners;
    }

    public void setExaminers(List<Examiner> examiners) {
        this.examiners = examiners;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}