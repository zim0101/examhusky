package com.app.examhusky.repository;

import com.app.examhusky.model.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Integer> {
    List<QuestionCategory> findAllByDeletedFalseAndDisabledFalse();
    Page<QuestionCategory> findAllByDeletedFalse(Pageable pageable);
}
