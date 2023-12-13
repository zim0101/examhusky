package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Examiner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examiner_generator")
    @SequenceGenerator(name = "examiner_generator", sequenceName = "examiner_seq")
    private Integer id;

    @OneToOne
    @NotNull
    private Account account;

    @ManyToMany
    @JoinTable(
            name = "exam_examiner",
            joinColumns = @JoinColumn(name = "examiner_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id"))
    private List<Exam> exams;

    @ManyToOne
    private Designation designation;

    private Boolean deleted = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}