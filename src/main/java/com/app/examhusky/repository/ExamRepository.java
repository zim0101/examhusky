package com.app.examhusky.repository;

import com.app.examhusky.model.Exam;
import com.app.examhusky.model.Examiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Page<Exam> findByDeletedFalseOrderByStartDateDesc(Pageable pageable);
    Page<Exam> findByDeletedFalse(Pageable pageable);
}
