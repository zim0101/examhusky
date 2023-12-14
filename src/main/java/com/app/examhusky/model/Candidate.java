package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
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
    @JoinTable(
            name = "exam_candidate",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private List<Exam> exams;

    @NotNull
    @Size(min = 14, max = 14, message = "Mobile number must have exactly 14 characters including " +
            "+88")
    private String mobileNumber;

    private String githubUrl;

    private String linkedinUrl;

    private Boolean deleted = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}