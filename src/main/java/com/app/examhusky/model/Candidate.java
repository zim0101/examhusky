package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_generator")
    @SequenceGenerator(name = "candidate_generator", sequenceName = "candidate_seq")
    private Integer id;

    @OneToOne
    @NotNull
    private Account account;

    @ManyToMany
    private List<Exam> exams;

    @Size(min = 14, max = 14, message = "Mobile number must have exactly 14 characters including " +
            "+88")
    private String mobileNumber;

    private String githubUrl;

    private String linkedinUrl;

    private Boolean deleted = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Candidate() {
    }

    public Candidate(Integer id, Account account, List<Exam> exams, String mobileNumber, String githubUrl,
                     String linkedinUrl, Boolean deleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.account = account;
        this.exams = exams;
        this.mobileNumber = mobileNumber;
        this.githubUrl = githubUrl;
        this.linkedinUrl = linkedinUrl;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
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