package com.app.examhusky.repository;

import com.app.examhusky.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Page<Question> findAllByDeletedFalse(Pageable pageable);
}
