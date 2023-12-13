package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Getter
@Setter
@Entity
public class QuestionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_category_generator")
    @SequenceGenerator(name = "question_category_generator", sequenceName = "question_category_seq")
    private Integer id;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String title;

    private Boolean deleted = false;

    private Boolean disabled = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}