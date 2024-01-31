package com.app.examhusky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

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

    public QuestionCategory() {
    }

    public QuestionCategory(Integer id, String title, Boolean deleted, Boolean disabled, Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.deleted = deleted;
        this.disabled = disabled;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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