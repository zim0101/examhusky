package com.app.examhusky.model;

import com.app.examhusky.model.enums.QuestionDifficulty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq")
    private Integer id;

    @NotNull
    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String title;

    private QuestionDifficulty difficultyLevel;

    @ManyToOne
    @NotNull
    private QuestionCategory category;

    @ManyToOne
    private Account editor;

    @ManyToMany
    private List<Exam> exams;

    private Boolean deleted = false;

    private Boolean disabled = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Question() {
    }

    public Question(Integer id, String title, QuestionDifficulty difficultyLevel, QuestionCategory category,
                    Account editor, List<Exam> exams, Boolean deleted, Boolean disabled,
                    Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.difficultyLevel = difficultyLevel;
        this.category = category;
        this.editor = editor;
        this.exams = exams;
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

    public QuestionDifficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(QuestionDifficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    public Account getEditor() {
        return editor;
    }

    public void setEditor(Account editor) {
        this.editor = editor;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
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