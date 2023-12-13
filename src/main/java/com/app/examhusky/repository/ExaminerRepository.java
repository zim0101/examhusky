package com.app.examhusky.repository;

import com.app.examhusky.model.Examiner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminerRepository extends JpaRepository<Examiner, Integer> {
    Page<Examiner> findAllByDeletedFalse(Pageable pageable);
}
